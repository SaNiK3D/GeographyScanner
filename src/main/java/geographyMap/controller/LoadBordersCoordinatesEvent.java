package geographyMap.controller;

/**
 * Created by User on 08.05.2017
 */
class LoadBordersCoordinatesEvent {
    private final String filePath;

    LoadBordersCoordinatesEvent(String filePath) {
        this.filePath = filePath;
    }

    String getFilePath() {
        return filePath;
    }

}