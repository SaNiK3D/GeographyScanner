package geographyMap.controller;

import eventbus.Callback;
import geographyMap.Function2Args;

/**
 * Created by User on 10.05.2017
 */
abstract class LoadSurfaceHeightsCallback implements Callback{
    abstract void onSuccess(Function2Args[] heights);
}
