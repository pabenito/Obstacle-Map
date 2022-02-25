/*
 * Author: Pedro Antonio Benito
 * Date: 23/03/2021
 */

package ObstacleMap;

import java.util.Random;

public class ObstacleMapGenerator {
	//Static parameters

		// Chars to print when toString over map
	public static final boolean PATH = false;
	public static final boolean WALL = true;

	// Inner map parameters

		// Inner variables
	private final MapConfiguration configuration;
	private final boolean [][] map;
	private final Random rnd;
	

	// Public

		// Constructors

	public ObstacleMapGenerator(int rows, int cols, MapTemplate template, Long seed) {
		this(rows, cols, (int) (rows * cols * template.WALLS), template.MAX_AROUND_WALLS, template.PROB_0, seed);
	}

	public ObstacleMapGenerator(int rows, int cols, int walls, int maxAroundWalls, double pOPoint, Long seed){
		configuration = new MapConfiguration(rows, cols, walls, maxAroundWalls, pOPoint, seed);
		map = new boolean[getRows()][getCols()];
		rnd = new Random();
		rnd.setSeed(getSeed());

		generateMap();
	}

		// Getters
	
	

	private int getRows() {
		return configuration.ROWS;
	}

	private int getCols() {
		return configuration.COLS;
	}

	private long getSeed() {
		return configuration.SEED;
	}

	private int getWalls(){
		return configuration.WALLS;
	}
	
	private double getP0Point(){
		return configuration.P0_POINT;
	}
	
	private int getMaxWallsAround(){
		return configuration.MAX_WALLS_AROUND;
	}

	public boolean[][] getMap() {
		return map;
	}

		// To represent map

	@Override
	// Show the obstacle map with the indicated char constant on the header
	public String toString() {
		MapPrinter printer = new MapPrinter(map);
		return printer.print();
	}

	// Private
		// Map generation

	private int generateMap(){
		Cell cell;
		boolean current;
		int wallsPlaced = 0;
		double pNotMaxAroundWalls = updateProbability(wallsPlaced); // First wall will be placed for sure

		// Place walls until they are all placed
		// and there is enough space in the map
		while(wallsPlaced <= getWalls()){
			cell = nextRandom(); // Generate next position randomly ensuring to be free

			// Tries to set a wall
			current =  PATH;

			// If there is at least one wall around but no more
			// than max assigned at header in getMaxWallsAround()
			if(maxWallsAround(cell)){
				current = WALL; // Set a wall with pMaxAroundWalls probability

				// If there are no walls around or more than allowed
			}else if(rnd.nextDouble() < pNotMaxAroundWalls){
				current = WALL; // Set a wall with pNotMaxAroundWalls probability
			}

			// If a wall was placed
			if(current == WALL){
				map[cell.row][cell.column] = current; // Place a wall
				wallsPlaced++; // Increase wallsPlaced of walls already placed
					pNotMaxAroundWalls = updateProbability(wallsPlaced); // SO IMPORTANT! UPDATE DYNAMIC PROBABILITY!
			}
		}

		return wallsPlaced;
	}

	private double updateProbability(int walls) {
		/*
		 * First wall will be placed with 100% probability, then will decrease exponentially with square function.
		 * I took update(walls) = 1-square(walls/walls_p0) because it begins in 1 (100% probability) for update(0) and reach 0 (0% probability) for update(walls_p0).
		 * walls_p0 = number of walls placed that represent NOT_getMaxWallsAround()_P0 of total cells of the map.
		 * You can plot the function here:  http://thewessens.net/ClassroomApps/Main/plot.html with 1-sqrt(x/MAX)
		 * 		substitute MAX for your (NOT_getMaxWallsAround()_P0 * getRows() * getCols()).
		 */

		return 1 - Math.sqrt(walls / (getP0Point() * getRows() * getCols()));
	}

	// Determine if there is least one wall around but no more than max assigned at MaxWallsAround
	// treating map cyclic, enabling it to be used like an infinite map with coherence.
	private boolean maxWallsAround(Cell cell) {
		return getWallsAround(cell) > 0 && getWallsAround(cell) <= getMaxWallsAround();
	}

	// Count how many walls are around the cell, without including it
	private int getWallsAround(Cell cell) {
		int result = 0;
		int row = cell.row - 1;
		while(row <= cell.row + 1 && result <= getMaxWallsAround()) {
			int column = cell.column - 1;
			while (column <= cell.column + 1 && result <= getMaxWallsAround()) {
				if (map[Math.floorMod(row, getRows())][Math.floorMod(column, getRows())] == WALL) { // treat map cyclic by applying modulus
					result++;
				}
				column++;
			}
			row++;
		}
		return result;
	}

	// Return a random position inside map.
	private Cell nextRandom(){
		int row, column;

		// Generate positions while isn't free
		do{
			row = rnd.nextInt(getRows());
			column = rnd.nextInt(getCols());
		}while (map[row][column] == WALL);

		return new Cell(row, column);
	}
}
