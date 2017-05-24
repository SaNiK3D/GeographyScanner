package geographyMap.controller;

import geographyMap.Coordinate;

/**
 * Created by User on 10.05.2017
 */
abstract class LoadBordersCoordinatesCallback implements Callback {

    abstract void onSuccess(Coordinate[] borderCoordinates);
}
