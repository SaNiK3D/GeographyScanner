package geographyMap.controller;

import eventbus.EventBus;
import geographyMap.Coordinate;
import geographyMap.Function2Args;
import geographyMap.GeographyMap;
import geographyMap.Grid;

import java.io.IOException;

/**
 * Created by User on 08.05.2017
 */
public class GeographyController {
    private GeographyMap geographyMap;
    private Function2Args[] surfaceHeights;
    private Coordinate[] borderCoordinates;

    private Thread interpolationThread;

    public GeographyController(EventBus eventBus) {
        eventBus.addHandler(LoadBordersCoordinatesEvent.class, this::loadBorderCoordinates);
        eventBus.addHandler(LoadSurfaceHeightsEvent.class, this::loadSurfaceHeights);
        eventBus.addHandler(InterpolateEvent.class, this::startInterpolation);
        eventBus.addHandler(InterruptInterpolationEvent.class, this::interruptInterpolation);
        eventBus.addHandler(SaveGridEvent.class, this::saveGrid);
    }

    private void loadSurfaceHeights(LoadSurfaceHeightsEvent event){
        try {
            surfaceHeights = CoordinatesLoader.getHeights(event.getFilePath());
            event.getCallback().onSuccess();
        } catch (IOException e) {
            event.getCallback().onFail(new RuntimeException(e));
        }
    }

    private void loadBorderCoordinates(LoadBordersCoordinatesEvent event){
        try {
            borderCoordinates = CoordinatesLoader.getBorderCoordinates(event.getFilePath());
            event.getCallback().onSuccess();
        } catch (IOException e) {
            event.getCallback().onFail(new RuntimeException(e));
        }
    }

    private void startInterpolation(InterpolateEvent event){
        interpolationThread = new Thread(() -> {
            geographyMap = new GeographyMap(borderCoordinates, event.getGridStep());
            Grid grid = geographyMap.interpolate(surfaceHeights);
            event.getCallback().onSuccess(grid);
        });

        interpolationThread.start();
    }

    private void interruptInterpolation(InterruptInterpolationEvent event){
        try {
            interpolationThread.join();
        } catch (InterruptedException e) {
            event.getCallback().onFail(new RuntimeException("Ошибка остановки интерполяции!", e));
        }
    }

    private void saveGrid(SaveGridEvent event){
        CoordinatesLoader.saveGridToFile(event.getGrid(), event.getFilePath());
        event.getCallback().onSuccess();
    }
}