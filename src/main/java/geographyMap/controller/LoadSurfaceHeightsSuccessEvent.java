package geographyMap.controller;

import geographyMap.Function2Args;

/**
 * Created by User on 24.05.2017
 */
public class LoadSurfaceHeightsSuccessEvent {
    private final Function2Args[] heights;

    public LoadSurfaceHeightsSuccessEvent(Function2Args[] heights) {
        this.heights = heights;
    }

    public Function2Args[] getHeights() {
        return heights;
    }
}
