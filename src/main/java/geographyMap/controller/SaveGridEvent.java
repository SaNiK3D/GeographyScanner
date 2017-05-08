package geographyMap.controller;

import eventbus.Event;
import geographyMap.Grid;

/**
 * Created by User on 09.05.2017
 */
class SaveGridEvent implements Event{
    private final Grid grid;
    private final String filePath;
    private final AbstractCallback callback;

    SaveGridEvent(Grid grid, String filePath, AbstractCallback callback) {
        this.grid = grid;
        this.filePath = filePath;
        this.callback = callback;
    }

    Grid getGrid() {
        return grid;
    }

    AbstractCallback getCallback() {
        return callback;
    }

    String getFilePath() {
        return filePath;
    }
}
