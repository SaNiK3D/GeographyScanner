package geographyMap.controller;

import eventbus.Event;

/**
 * Created by User on 08.05.2017
 */
class LoadBordersCoordinatesEvent implements Event {
    private final String filePath;
    private final LoadBordersCoordinatesCallback callback;

    LoadBordersCoordinatesEvent(String filePath, LoadBordersCoordinatesCallback callback) {
        this.filePath = filePath;
        this.callback = callback;
    }

    String getFilePath() {
        return filePath;
    }

    LoadBordersCoordinatesCallback getCallback() {
        return callback;
    }
}