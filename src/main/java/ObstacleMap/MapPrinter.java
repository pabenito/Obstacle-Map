package ObstacleMap;

import java.util.StringJoiner;

public class MapPrinter {
    public static final char PATH = '░';
    public static final char WALL = '█';

    private boolean[][] map;

    MapPrinter(boolean[][] map){
        this.map = map;
    }

    public void setMap(boolean[][] map) {
        this.map = map;
    }

    public String print(){
        StringJoiner sj = new StringJoiner("\n", "\n", "\n");
        StringBuilder sb = new StringBuilder(COLS);

        for(int r = 0; r < ROWS; r++) {
            for(int c = 0; c < COLS; c++) {
                if(map[r][c] == null){
                    sb.append(PATH);
                }else {
                    sb.append(map[r][c]);
                }
            }
            sj.add(sb);
            sb = new StringBuilder(COLS);
        }

        return sj.toString();
    }
}
