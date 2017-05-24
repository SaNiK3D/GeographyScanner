package geographyMap.controller;

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

    public GeographyController(GeographyMap geographyMap) {
        this.geographyMap = geographyMap;

    }

    void loadSurfaceHeights(String path, LoadSurfaceHeightsCallback callback){
        try {
            surfaceHeights = CoordinatesLoader.getHeights(path);
            callback.onSuccess(surfaceHeights);
        } catch (IOException e) {
            callback.onFail(new RuntimeException(e));
        }
    }

    void loadBorderCoordinates(String path, LoadBordersCoordinatesCallback callback){
        try {
            borderCoordinates = CoordinatesLoader.getBorderCoordinates(path);
            callback.onSuccess(borderCoordinates);
        } catch (IOException e) {
            callback.onFail(new RuntimeException(e));
        }
    }

    void startInterpolation(int gridStep, InterpolateCallback callback){
        Thread interpolationThread = new Thread(() -> {
            Coordinate[] sortedCoordinates = geographyMap.setBorders(borderCoordinates, gridStep);
            Grid grid = geographyMap.interpolate(surfaceHeights);
            if (grid != null)
                callback.onSuccess(grid, sortedCoordinates);
        });

        interpolationThread.start();
    }

    void interruptInterpolation(AbstractCallback callback){
        geographyMap.terminate();
        callback.onSuccess();
    }

    void saveGrid(String path, AbstractCallback callback){
        try {
            CoordinatesLoader.saveGridToFile(geographyMap.getGrid(), path);
        } catch (IOException e) {
            callback.onFail(new RuntimeException(e));
        }
        callback.onSuccess();
    }
}