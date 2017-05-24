package geographyMap.controller;

/**
 * Created by User on 09.05.2017
 */
class StartInterpolationEvent {
    private final int gridStep;

    StartInterpolationEvent(int gridStep) {
        this.gridStep = gridStep;
    }

    int getGridStep() {
        return gridStep;
    }
}
