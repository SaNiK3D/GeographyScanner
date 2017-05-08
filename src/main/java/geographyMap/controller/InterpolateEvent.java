package geographyMap.controller;

import eventbus.Event;

/**
 * Created by User on 09.05.2017
 */
class InterpolateEvent implements Event{
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
