import ObstacleMap.*;

import java.util.Random;

public class ObstacleMapTester {
    private static final int SIDE = 40;
    private static boolean random = false;
    private static int numMaps;

    public static void main (String[] args){
        long beginning, end;

        beginning = System.currentTimeMillis(); // Save beginning time in milliseconds
        if(random){
            numMaps = randomTest();
        }else{
            numMaps = templateTest();
        }
        end = System.currentTimeMillis(); // Save ending time in milliseconds

        System.out.println("\nThe elapse time for " + numMaps + " obstacles map was : " + (end - beginning) + " ms. Mean elapse time per map was: " + (double) (end - beginning) / numMaps + "ms.");

        System.exit(0);
    }

    // Generate NUM_MAPS with side between MIN_SIDE and MAX_SIDE
    private static int randomTest(){
        final int NUM_TEST = 100;
        ObstacleMapGenerator map;
        Random rnd = new Random();
        int counter = 0, rows, cols, walls, maxAroundWalls;
        double p0;
        long seed;

        while (counter < NUM_TEST){
            seed = rnd.nextInt();
            rows = SIDE;
            cols = (int) (rows * 2.75); // Place this scale for look like a square, because line spacing
            maxAroundWalls = nextIntInRange(rnd, 1, 8);
            p0 = rnd.nextDouble() * 0.99 + 0.01;
            walls = nextIntInRange(rnd, 0.1, MapConfiguration.getMaxWalls(maxAroundWalls), rows * cols);

            System.out.println("Obstacle map " + counter + ": Seed = " + seed + "; rows = " + rows + "; cols = " + cols + "; walls = "
                    + walls + "; walls/cells: " + Math.round((double) 100* walls / (rows * cols)) + "%. Constants: MAX_AROUND_WALLS: "
                    + maxAroundWalls + "; PO_POINT: " + Math.round(100 * p0) + "%.");

            map = new ObstacleMapGenerator(rows, cols, walls, maxAroundWalls, p0, seed);
            //map.setBeginningAndEnding();

            System.out.println(map.toString());

            counter++;
        }

        return NUM_TEST;
    }

    private static int templateTest(){
        final int NUM_TEST = 1;
        final MapTemplate[] templates = {MapTemplate.ALIEN_INVASION, MapTemplate.BIG_WORLD, MapTemplate.HARD_MODE, MapTemplate.MAZE, MapTemplate.STICK_ROOM};
        ObstacleMapGenerator map;
        Random rnd = new Random();
        int counter, templateNum, rows, cols, walls, maxAroundWalls;
        double p0;
        long seed;

        templateNum = 0;
        while (templateNum < templates.length){
            System.out.println("Template: " + templates[templateNum].NAME);
            counter = 0;
            while (counter < NUM_TEST){
                seed = rnd.nextInt();
                rows = nextIntInRange(rnd, SIDE, SIDE);
                cols = (int) (rows * 2.75); // Place this scale for look like a square, because line spacing
                walls = (int) (templates[templateNum].WALLS * rows * cols);
                maxAroundWalls = templates[templateNum].MAX_AROUND_WALLS;
                p0 = templates[templateNum].PROB_0;

                System.out.println("Obstacle map " + counter + ": Seed = " + seed + "; rows = " + rows + "; cols = " + cols + "; walls = "
                        + walls + "; walls/cells: " + Math.round((double) 100* walls / (rows * cols)) + "%. Constants: MAX_AROUND_WALLS: "
                        + maxAroundWalls + "; PO_POINT: " + Math.round(100 * p0) + "%.");

                map = new ObstacleMapGenerator(rows, cols, walls, maxAroundWalls, p0, seed);
                //map.setBeginningAndEnding();

                System.out.println(map.toString());

                counter++;
            }
            templateNum++;
        }

        return NUM_TEST * templates.length;
    }

    // Being n a number, this function return an integer between n*minPercent and n*maxPercent.
    // It is taken a Random by argument in order to that that rnd could be a Random with a seed already placed
    private static int nextIntInRange(Random rnd, double minPercent, double maxPercent, int n){
        int min = (int) Math.ceil(n * minPercent);
        int max = (int) Math.floor(n * maxPercent);

        return nextIntInRange(rnd, min, max);
    }

    // Return a random integer between min and max, both included.
    // It is taken a Random by argument in order to that that rnd could be a Random with a seed already placed
    private static int nextIntInRange(Random rnd, int min, int max){
        int walls;

        try{
            walls =  rnd.nextInt(max - min) +  min;
        }catch (IllegalArgumentException e){
            walls = min;
        }

        return walls;
    }
}
