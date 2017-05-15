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
                view.setBorderCoordinates(borderCoordinates);
            }

            @Override
            public void onFail(RuntimeException e) {

            }
        }));
    }

    public void loadSurfaceHeights(String filePath){
        eventBus.post(new LoadSurfaceHeightsEvent(filePath, new LoadSurfaceHeightsCallback() {
            @Override
            void onSuccess(Function2Args[] heights) {
                view.setHeights(heights);
            }

            @Override
            public void onFail(RuntimeException e) {

            }
        }));
    }

    public void startInterpolation(int gridStep){
        eventBus.post(new InterpolateEvent(gridStep, new InterpolateCallback() {
            @Override
            void onSuccess(Grid grid, Coordinate[] borderCoordinates) {
                view.stopInterpolation();
                view.drawMap(grid, borderCoordinates);
                view.saveGrid(grid);
            }

            @Override
            public void onFail(RuntimeException e) {
                view.stopInterpolation();
            }
        }));

        view.startInterpolation();
    }

    public void interruptInterpolation(){
        eventBus.post(new InterruptInterpolationEvent(new AbstractCallback() {
            @Override
            void onSuccess() {

            }

            @Override
            public void onFail(RuntimeException e) {

            }
        }));
    }

    public void saveGridToFile(String filePath) {
        eventBus.post(new SaveGridEvent(filePath, new AbstractCallback() {
            @Override
            void onSuccess() {

            }

            @Override
            public void onFail(RuntimeException e) {

            }
        }));
    }
}