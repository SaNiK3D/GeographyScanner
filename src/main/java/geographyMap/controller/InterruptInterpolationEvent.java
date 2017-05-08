package geographyMap.controller;

import eventbus.Event;

/**
 * Created by User on 09.05.2017
 */
class InterruptInterpolationEvent implements Event {
    private final AbstractCallback callback;

    InterruptInterpolationEvent(AbstractCallback callback) {
        this.callback = callback;
    }

    AbstractCallback getCallback() {
        return callback;
    }
}