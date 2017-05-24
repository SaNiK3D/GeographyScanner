package geographyMap.controller;

/**
 * Created by User on 08.05.2017
 */
class LoadSurfaceHeightsEvent {
    private final String filePath;

    LoadSurfaceHeightsEvent(String filePath) {
        this.filePath = filePath;
    }

    String getFilePath() {
        return filePath;
    }
}