package geographyMap.controller;

/**
 * Created by User on 25.05.2017
 */
public class SuccessEvent {
    private final String message;
    private final String title;

    public SuccessEvent(String message, String title) {
        this.message = message;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
