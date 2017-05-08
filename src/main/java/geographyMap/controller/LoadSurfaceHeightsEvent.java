package geographyMap.controller;

import eventbus.Event;

/**
 * Created by User on 08.05.2017
 */
class LoadSurfaceHeightsEvent implements Event {
    private final String filePath;
    private final AbstractCallback callback;

    LoadSurfaceHeightsEvent(String filePath, AbstractCallback callback) {
        this.filePath = filePath;
        this.callback = callback;
    }

    String getFilePath() {
        return filePath;
    }

    AbstractCallback getCallback() {
        return callback;
    }
}