package geographyMap.controller;

import eventbus.Event;

/**
 * Created by User on 08.05.2017
 */
class LoadSurfaceHeightsEvent implements Event {
    private final String filePath;
    private final LoadFileCallback callback;

    LoadSurfaceHeightsEvent(String filePath, LoadFileCallback callback) {
        this.filePath = filePath;
        this.callback = callback;
    }

    String getFilePath() {
        return filePath;
    }

    LoadFileCallback getCallback() {
        return callback;
    }
}