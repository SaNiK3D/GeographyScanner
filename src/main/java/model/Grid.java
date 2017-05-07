package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06.05.2017
 */
public class Grid {
    private List<Cell>[] heights;

    public Grid(int sizeX) {
        //noinspection unchecked
        heights = new List[sizeX];
    }

    public List<Cell>[] getHeights() {
        return heights;
    }

    public void setHeightAt(int index, int columnSize){
        heights[index] = new ArrayList<>(columnSize);
    }
}
