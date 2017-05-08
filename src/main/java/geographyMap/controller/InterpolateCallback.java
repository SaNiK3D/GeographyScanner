package geographyMap.controller;

import eventbus.Callback;
import geographyMap.Grid;

/**
 * Created by User on 09.05.2017
 */
abstract class InterpolateCallback implements Callback {

    abstract void onSuccess(Grid grid);
}
