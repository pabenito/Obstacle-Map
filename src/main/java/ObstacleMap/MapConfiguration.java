package ObstacleMap;

public class MapConfiguration {
    // Inner constants
    public final int ROWS;
    public final int COLS;
    public final int WALLS;
    public final int MAX_WALLS_AROUND; /* Set the maximum number of walls that can have a free cell around it
     * 	to have a P_MAX_AROUND_WALLS of probability to be placed.
     * It is an interesting parameter to change. So powerful.
     */
    public final double P0_POINT; /* When number of walls reach (100 * PO_POINT)%
     * 	of cells, probability of placing another wall with no walls
     * 	around reaches 0.
     * It is an interesting parameter to change. So powerful.
     * 	Can take values from 0.01 to 1.
     * We can increase it for less connected maps (easier) or
     * 	decrease it for more connected maps (more difficult).
     */
    public final long SEED;

    public MapConfiguration(int ROWS, int COLS, int WALLS, int MAX_AROUND_WALLS, double P0_POINT, long SEED) {
        chekArguments(ROWS, COLS, WALLS, MAX_AROUND_WALLS, P0_POINT);

        this.ROWS = ROWS;
        this.COLS = COLS;
        this.WALLS = WALLS;
        this.MAX_WALLS_AROUND = MAX_AROUND_WALLS;
        this.P0_POINT = P0_POINT;
        this.SEED = SEED;
    }

    private void chekArguments(int rows, int cols, int walls, int maxWallsAround
            , double pOPoint) {
        if(maxWallsAround < 1 || maxWallsAround > 8){
            throw new IllegalArgumentException("Max around walls must be between 0 and 8.");
        }
        if(pOPoint < 0.01 || pOPoint > 1){
            throw new IllegalArgumentException("The point (wall%) when the probability of place a wall out of restrictions reach 0 must be between 0.01 and 1.");
        }
        if(rows < 2 || cols < 2){
            throw new IllegalArgumentException("Rows and cols must be >= 2.");
        }
        if (walls < 0 || walls > rows * cols * getMaxWalls(maxWallsAround)){
            throw new IllegalArgumentException("The number of walls shoud be between " + 0 + "% and " + getMaxWalls(maxWallsAround) * 100 + "% of cells, both included.");
        }
    }

    // Control

    // Return maximum walls% that can be placed in a map in terms of getMaxWallsAround().
    // That values were obtained in tests.
    public static double getMaxWalls(int maxWallsAround){
        double maxWalls;

        switch (maxWallsAround){
            case 1: maxWalls = 0.33; break;
            case 2: maxWalls = 0.45; break;
            case 3: maxWalls = 0.54; break;
            case 4: maxWalls = 0.63; break;
            case 5: maxWalls = 0.72; break;
            case 6: maxWalls = 0.81; break;
            case 7: maxWalls = 0.88; break;
            case 8: maxWalls = 1; break;
            default: throw new IllegalArgumentException("STATIC CONSTANT ERROR: getMaxWallsAround() must be between 1 and 8, please replace it.");
        }

        return maxWalls;
    }
}
