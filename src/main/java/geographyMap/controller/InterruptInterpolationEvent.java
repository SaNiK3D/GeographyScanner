package geographyMap.controller;

/**
 * Created by User on 09.05.2017
 */
class InterruptInterpolationEvent {
    private final AbstractCallback callback;

    InterruptInterpolationEvent(AbstractCallback callback) {
        this.callback = callback;
    }

    AbstractCallback getCallback() {
        return callback;
    }
}