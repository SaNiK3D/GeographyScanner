package geographyMap.controller;

/**
 * Created by User on 09.05.2017
 */
class SaveGridEvent {
    private final String filePath;

    SaveGridEvent(String filePath) {
        this.filePath = filePath;
    }

    String getFilePath() {
        return filePath;
    }
}
