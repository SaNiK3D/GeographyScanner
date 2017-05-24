package geographyMap.controller;

/**
 * Created by User on 25.05.2017
 */
public class FailEvent {
    private final String message;
    private final String title;

    public FailEvent(String message, String title) {
        this.message = message;
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }
}
