package geographyMap.controller;

/**
 * Created by User on 09.05.2017
 */
class SaveGridEvent {
    private final String filePath;
    private final AbstractCallback callback;

    SaveGridEvent(String filePath, AbstractCallback callback) {
        this.filePath = filePath;
        this.callback = callback;
    }

    AbstractCallback getCallback() {
        return callback;
    }

    String getFilePath() {
        return filePath;
    }
}
