package geographyMap.controller;

import geographyMap.Coordinate;
import geographyMap.Grid;

/**
 * Created by User on 25.05.2017
 */
public class StartInterpolationSucceessEvent {
    private final Grid grid;
    private final Coordinate[] borders;

    public StartInterpolationSucceessEvent(Grid grid, Coordinate[] borders) {
        this.grid = grid;
        this.borders = borders;
    }

    public Grid getGrid() {
        return grid;
    }

    public Coordinate[] getBorders() {
        return borders;
    }
}
