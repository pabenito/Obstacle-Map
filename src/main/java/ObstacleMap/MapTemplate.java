package ObstacleMap;

public class MapTemplate{
    // Default maps

    public static final MapTemplate MAZE = new MapTemplate("Maze", 2, 0.4, 0.26);
    public static final MapTemplate STICK_ROOM = new MapTemplate("Stick Room", 1, 0.2, 0.06);
    public static final MapTemplate BIG_WORLD = new MapTemplate("Big World", 7, 0.37, 0.012);
    public static final MapTemplate HARD_MODE = new MapTemplate("Hard Mode", 3, 0.33, 0.42);
    public static final MapTemplate ALIEN_INVASION = new MapTemplate("Alien Invasion", 5, 0.2, 0.068);

    // Parameters
    public final int MAX_AROUND_WALLS;
    public final double WALLS;
    public final double PROB_0;
    public final String NAME;

    public MapTemplate(String name, int maxAroundWalls, double walls, double prob_0){
        NAME = name;
        MAX_AROUND_WALLS = maxAroundWalls;
        WALLS = walls;
        PROB_0 = prob_0;
    }
}
