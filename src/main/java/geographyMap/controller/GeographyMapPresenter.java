package geographyMap.controller;

import eventbus.EventBus;
import geographyMap.Coordinate;
import geographyMap.Function2Args;
import geographyMap.GeographyMap;
import geographyMap.GeographyMapView;

/**
 * Created by User on 09.05.2017
 */
public class GeographyMapPresenter {
    private final GeographyMapView view;

    public GeographyMapPresenter(GeographyMapView view) {
        this.view = view;
    }

    public static void main(String[] args) {
        GeographyMap map = new GeographyMap();
        EventBus eventBus = new EventBus();
        GeographyController controller = new GeographyController(eventBus, map);
        eventBus.post(new LoadBordersCoordinatesEvent("C:\\Users\\User\\IdeaProjects\\GeographyScanner\\files\\squareBorders.csv",
                new LoadBordersCoordinatesCallback() {
                    @Override
                    void onSuccess(Coordinate[] borderCoordinates) {
                        for (Coordinate c :
                                borderCoordinates) {
                            System.out.println(c.x + ";" + c.y);
                        }
                    }

                    @Override
                    public void onFail(RuntimeException e) {
                        System.out.println("Ошибка!");
                    }
                }));
        eventBus.post(new LoadSurfaceHeightsEvent("C:\\Users\\User\\IdeaProjects\\GeographyScanner\\files\\triangleHeights.csv",
                new LoadSurfaceHeightsCallback() {
                    @Override
                    void onSuccess(Function2Args[] heights) {
                        for (Function2Args fun :
                                heights) {
                            System.out.println(fun.coordinate.x + ";" + fun.coordinate.y + ";" + fun.value);
                        }
                    }

                    @Override
                    public void onFail(RuntimeException e) {

                    }
                }));
    }
}