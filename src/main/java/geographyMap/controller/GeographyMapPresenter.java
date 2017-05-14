package geographyMap.controller;

import eventbus.EventBus;
import geographyMap.*;

/**
 * Created by User on 09.05.2017
 */
public class GeographyMapPresenter {
    private final GeographyMapView view;
    private final EventBus eventBus;

    public GeographyMapPresenter(GeographyMapView view, EventBus eventBus) {
        this.view = view;
        this.eventBus = eventBus;
    }

    public void loadBorders(String filePath){
        eventBus.post(new LoadBordersCoordinatesEvent(filePath, new LoadBordersCoordinatesCallback() {
            @Override
            void onSuccess(Coordinate[] borderCoordinates) {

            }

            @Override
            public void onFail(RuntimeException e) {

            }
        }));
    }

    public void loadSurfaceHeights(String filePath){

    }

    public void startInterpolation(){

    }
}