package geographyMap;


/**
 * Created by User on 06.05.2017
 */
public class Grid {
    private Cell[][] heights;

    public Grid(int width, int height) {
        //noinspection unchecked
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

    public void setHeight(int column, int row, double value){
        heights[column][row].setValue(value);
    }
}
