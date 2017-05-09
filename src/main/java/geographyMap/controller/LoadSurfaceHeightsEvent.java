package geographyMap.controller;

import eventbus.Event;

/**
 * Created by User on 08.05.2017
 */
class LoadSurfaceHeightsEvent implements Event {
    private final String filePath;
    private final LoadSurfaceHeightsCallback callback;

    LoadSurfaceHeightsEvent(String filePath, LoadSurfaceHeightsCallback callback) {
        this.filePath = filePath;
        this.callback = callback;
    }

    String getFilePath() {
        return filePath;
    }

    LoadSurfaceHeightsCallback getCallback() {
        return callback;
    }
}