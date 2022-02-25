package ObstacleMap;

import java.util.StringJoiner;

public class MapPrinter {
    public static final char PATH = '░';
    public static final char WALL = '█';

    private boolean[][] map;

    MapPrinter(boolean[][] map){
        this.map = map;
    }

    public String print(){
        StringJoiner sj = new StringJoiner("\n");
        StringBuilder sb = new StringBuilder(getCols());

        for(int r = 0; r < getRows(); r++) {
            for(int c = 0; c < getCols(); c++) {
                if(map[r][c] == ObstacleMapGenerator.PATH){
                    sb.append(PATH);
                }else if(map[r][c] == ObstacleMapGenerator.WALL){
                    sb.append(WALL);
                }
            }
            sj.add(sb);
            sb = new StringBuilder(getCols());
        }

        return sj.toString();
    }

    private int getRows(){
        return map.length;
    }

    private int getCols(){
        return map[0].length;
    }
}
