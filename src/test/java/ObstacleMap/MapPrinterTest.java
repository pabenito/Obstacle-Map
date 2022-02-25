package ObstacleMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapPrinterTest {
    @Test
    void print() {
        boolean[][] map =
                {
                        {ObstacleMapGenerator.WALL, ObstacleMapGenerator.PATH},
                        {ObstacleMapGenerator.WALL, ObstacleMapGenerator.PATH}
                };
        String expectedPrint = String.format("%c%c\n%c%c", MapPrinter.WALL, MapPrinter.PATH, MapPrinter.WALL, MapPrinter.PATH);
        MapPrinter printer = new MapPrinter(map);
        assertEquals(printer.print(), expectedPrint);
    }
}