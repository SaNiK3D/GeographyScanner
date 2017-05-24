package geographyMap.controller;

import geographyMap.Coordinate;

/**
 * Created by User on 24.05.2017
 */
public class LoadBordersCoordinatesSuccessEvent {
    private final Coordinate[] borders;

    public LoadBordersCoordinatesSuccessEvent(Coordinate[] borders) {
        this.borders = borders;
    }

    public Coordinate[] getBorders() {
        return borders;
    }
}
