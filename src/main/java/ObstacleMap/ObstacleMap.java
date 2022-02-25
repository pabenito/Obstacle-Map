/*
 * Author: Pedro Antonio Benito
 * Date: 23/03/2021
 */

package ObstacleMap;

import java.util.Random;
import java.util.StringJoiner;

public class ObstacleMap {
	//Static parameters

		// Chars to print when toString over map
	public static final boolean PATH = false;
	public static final boolean WALL = true;

		// Probabilities
	public static final double P_MAX_AROUND_WALLS = 1; /* If there is at least one wall around the current cell but no more than
													   	* 	MAX_AROUND_WALLS, the probability of placing a wall is 100%.
													   	* It is NOT recommended to change this constant, only if you want a
													   	* 	disconnected map. Then can take values between 0 and 1.
													   	*/
		// Error control
	public static final int MAX_CELLS_TESTED = 1000000000; // Over that amount it is thrown an OutOfMemoryError.
	public static final boolean ERROR_IF_IS_WALL = false; // True = Throw an error when trying to set beginning/ending over a wall
														  // False = Place the beginning/ending anyway
	// Inner map parameters

		// Inner variables
    private Cell beginning; // Initial cell
	private Cell ending; // Final cell
	private boolean [][] map;
	private Random rnd;

		// Inner constants
	private final long SEED;
	private final int ROWS;
	private final int COLS;
	private final int WALLS;
	private final double MAX_AROUND_WALLS; /* Set the maximum number of walls that can have a free cell around it
										    * 	to have a P_MAX_AROUND_WALLS of probability to be placed.
										    * It is an interesting parameter to change. So powerful.
										    */
	private final double PO_POINT; /* When number of walls reach (100 * PO_POINT)%
									* 	of cells, probability of placing another wall with no walls
									* 	around reaches 0.
									* It is an interesting parameter to change. So powerful.
									* 	Can take values from 0.01 to 1.
									* We can increase it for less connected maps (easier) or
									* 	decrease it for more connected maps (more difficult).
									*/

	// Public

		// Constructors

	public ObstacleMap(long seed, int side, MapTemplate template) {
		this(seed, side, side, template);
	}

	public ObstacleMap(long seed, int rows, int cols, MapTemplate template) {
		this(seed, rows, cols, (int) (rows * cols * template.WALLS), template.MAX_AROUND_WALLS, template.PROB_0);
	}

	public ObstacleMap(long seed, int side, int walls, int maxAroundWalls, double pOPoint) {
		this(seed, side, side, walls, maxAroundWalls, pOPoint);
	}

	public ObstacleMap(long seed, int rows, int cols, int walls, int maxAroundWalls, double pOPoint){
		chekArguments(rows, cols, walls, maxAroundWalls, pOPoint);

		SEED = seed;
		ROWS = rows;
		COLS = cols;
		WALLS = walls;
		MAX_AROUND_WALLS = maxAroundWalls;
		PO_POINT = pOPoint;
		map = new boolean[ROWS][COLS];
		rnd = new Random();
		rnd.setSeed(SEED);

		generateMap(walls);
	}

		// Getters

	public int getRows() {
		return ROWS;
	}

	public int getCols() {
		return COLS;
	}

	public long getSeed() {
		return SEED;
	}

	public int getWalls(){
		return WALLS;
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

	// True indicate wall, false path
	public boolean[][] getBoolMap(){
		boolean[][] boolMap = new boolean[ROWS][COLS];

		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				if(map[r][c] != null){
					boolMap[r][c] = true;
				}
			}
		}

		return boolMap;
	}

	// Private

		// Map generation

	private int generateMap(int walls){
		Cell pos; // position
		Character current;
		Integer r, c; // row & col

		double randomProb; // Random probability
		double pMaxAroundWalls =  P_MAX_AROUND_WALLS;
		double pNotMaxAroundWalls = updateProbability(0); // First wall will be placed for sure
		int counter;
		counter = 0;

		// Place walls until they are all placed
		// and there is enough space in the map
		while(counter <= walls){
			pos = nextRandom(); // Generate next position randomly ensuring to be free

			// Tries to set a wall
			current =  null;
			randomProb = rnd.nextDouble(); // Generate the probability randomly: 0 <= p <= 1

			// If there is at least one wall around but no more
			// than max assigned at header in MAX_AROUND_WALLS
			if(maxWallsAround(pos.row, pos.column) && randomProb < pMaxAroundWalls){
				current = WALL; // Set a wall with pMaxAroundWalls probability

				// If there are no walls around or more than allowed
			}else if(randomProb < pNotMaxAroundWalls){
				current = WALL; // Set a wall with pNotMaxAroundWalls probability
			}

			// If a wall was placed
			if(current != null){
				map[pos.row][pos.column] = current; // Place a wall
				counter++; // Increase counter of walls already placed
					pNotMaxAroundWalls = updateProbability(counter); // SO IMPORTANT! UPDATE DYNAMIC PROBABILITY!
			}
		}

		return counter;
	}

	private double updateProbability(int walls) {
		/*
		 * First wall will be placed any then will decrease exponentially with square function.
		 * I took update(walls) = 1-square(walls/walls_p0) because it begins in 1 (100% probability) for update(0) and reach 0 (0% probability) for update(walls_p0).
		 * walls_p0 = number of walls placed that represent NOT_MAX_AROUND_WALLS_P0 of total cells of the map.
		 * You can plot the function here:  http://thewessens.net/ClassroomApps/Main/plot.html with 1-sqrt(x/MAX)
		 * 		substitute MAX for your (NOT_MAX_AROUND_WALLS_P0 * ROWS * COLS).
		 */

		return 1 - Math.sqrt(walls / (PO_POINT * ROWS * COLS));
	}

	// Determine if there is least one wall around but no more than max assigned at header in MAX_AROUND_WALLS
	// treating map cyclic, enabling it to be used like an infinite map with coherence.
	private boolean maxWallsAround(int row, int col) {
		int r, c, rMod, cMod, counter;

		r = row-1;
		counter = 0;

		// Count walls around while it doesn't count at least 1 over MAX_AROUND_WALLS
		while(r <= row + 1 && counter <= MAX_AROUND_WALLS) {
			c = col - 1;
			while (c <= col + 1 && counter <= MAX_AROUND_WALLS) {
				// treat map cyclic by applying modulus
				rMod = Math.floorMod(r, ROWS);
				cMod = Math.floorMod(c, COLS);

				// Ensure not null and then check if it is a wall. Could be beginning or ending
				if (map[rMod][cMod] != null && map[rMod][cMod] == WALL) {
					counter++; // Increase counter
				}
				c++;
			}
			r++;
		}

		return counter > 0 && counter <= MAX_AROUND_WALLS;
	}

	// Return a random position inside map.
	private Cell nextRandom(){
		int row, column;

		// Generate positions while isn't free
		do{
			row = rnd.nextInt(ROWS);
			column = rnd.nextInt(COLS);
		}while (map[row][column] != null);

		return new Cell(row, column);
	}

		// Control

	// Return maximum walls% that can be placed in a map in terms of MAX_AROUND_WALLS.
	// That values were obtained in tests.
	public static double getMaxWalls(int maxArondWalls){
		double maxWalls;

		switch (maxArondWalls){
			case 1: maxWalls = 0.33; break;
			case 2: maxWalls = 0.45; break;
			case 3: maxWalls = 0.54; break;
			case 4: maxWalls = 0.63; break;
			case 5: maxWalls = 0.72; break;
			case 6: maxWalls = 0.81; break;
			case 7: maxWalls = 0.88; break;
			case 8: maxWalls = 1; break;
			default: throw new IllegalArgumentException("STATIC CONSTANT ERROR: MAX_AROUND_WALLS must be between 1 and 8, please replace it.");
		}

		return maxWalls;
	}

	private void chekArguments(int rows, int cols, int walls, int maxAroundWalls, double pOPoint) {
		if(maxAroundWalls < 1 || maxAroundWalls > 8){
			throw new IllegalArgumentException("Max around walls must be between 0 and 8.");
		}
		if(pOPoint < 0.01 || pOPoint > 1){
			throw new IllegalArgumentException("The point (wall%) when the probability of place a wall out of restrictions reach 0 must be between 0.01 and 1.");
		}
		if(rows < 2 || cols < 2){
			throw new IllegalArgumentException("Rows and cols must be >= 2.");
		}
		if(rows * cols > MAX_CELLS_TESTED){
			throw new IllegalArgumentException("The number of cells of your map can't be saved on memory space, please reduce them.\nThe maximun number of cells in test was " + MAX_CELLS_TESTED + ".");
		}
		if (walls < 0 || walls > rows * cols * getMaxWalls(maxAroundWalls)){
			throw new IllegalArgumentException("The number of walls shoud be between " + 0 + "% and " + getMaxWalls(maxAroundWalls) * 100 + "% of cells, both included.");
		}
	}

	private void checkRowsAndCols(int rows, int cols){
		if(rows < 0 || cols < 0 || rows >= ROWS || cols >= COLS){
			throw new IllegalArgumentException("Position(" + rows + "," + cols + ") is out of map.");
		}
	}

	private void checkIsPath(int row, int col){
		if(map[row][col] != null && map[row][col] == WALL){
			throw new IllegalArgumentException("The cell (" + row + "," + col + ") is a wall, so can't be places here the beginning / ending.");
		}
	}
}
