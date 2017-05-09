package geographyMap;


/**
 * Created by User on 06.05.2017
 */
public class Grid {
    private final Cell[][] heights;
    private final int minX;
    private final int minY;
    private final int step;

    public Grid(int width, int height, int minX, int minY, int step) {
        this.minX = minX;
        this.minY = minY;
        this.step = step;
        heights = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                heights[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getHeights() {
        return heights;
    }

    public void setHeight(int column, int row, int value){
        heights[column][row].setValue(value);
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getStep() {
        return step;
    }
}
