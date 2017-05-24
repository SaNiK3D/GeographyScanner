package geographyMap.controller;

/**
 * Created by User on 09.05.2017
 */
class InterpolateEvent {
    private final int gridStep;
    private final InterpolateCallback callback;

    InterpolateEvent(int gridStep, InterpolateCallback callback) {
        this.gridStep = gridStep;
        this.callback = callback;
    }

    InterpolateCallback getCallback() {
        return callback;
    }

    int getGridStep() {
        return gridStep;
    }
}
