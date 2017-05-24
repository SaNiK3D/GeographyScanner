package geographyMap.controller;

import geographyMap.*;

/**
 * Created by User on 09.05.2017
 */
public class GeographyMapPresenter {
    private final GeographyMapView view;
    private final GeographyController controller;

    public GeographyMapPresenter(GeographyMapView view, GeographyController controller) {
        this.view = view;
        this.controller = controller;
    }

    public void loadBorders(String filePath){
        controller.loadBorderCoordinates(filePath, new LoadBordersCoordinatesCallback() {
            @Override
            void onSuccess(Coordinate[] borderCoordinates) {
                view.setBorderCoordinates(borderCoordinates);
                view.showInformMessage("Файл границ успешно загружен", "Загрузка файла границ");
            }

            @Override
            public void onFail(RuntimeException e) {
                view.showErrorMessage("Ошибка при загрузки файла границ!", "Загрузка файла");
            }
        });
    }

    public void loadSurfaceHeights(String filePath){
        controller.loadSurfaceHeights(filePath, new LoadSurfaceHeightsCallback() {
            @Override
            void onSuccess(Function2Args[] heights) {
                view.setHeights(heights);
                view.showInformMessage("Файл высот успешно загружен", "Загрузка файла высот");
            }

            @Override
            public void onFail(RuntimeException e) {
                view.showErrorMessage("Ошибка при загрузки файла высот!", "Загрузка файла");
            }
        });
    }

    public void startInterpolation(int gridStep){
        view.startInterpolation();
        controller.startInterpolation(gridStep, new InterpolateCallback() {
            @Override
            void onSuccess(Grid grid, Coordinate[] borderCoordinates) {
                view.stopInterpolation();
                view.drawMap(grid, borderCoordinates);
            }

            @Override
            public void onFail(RuntimeException e) {
                view.stopInterpolation();
            }
        });
    }

    public void interruptInterpolation(){
        controller.interruptInterpolation(new AbstractCallback() {
            @Override
            void onSuccess() {
                view.stopInterpolation();
                view.showInformMessage("Создание карты прервано!", "Прерывание");
            }

            @Override
            public void onFail(RuntimeException e) {
                view.stopInterpolation();
                view.showErrorMessage("Ошибка прерывания создания карты!", "Прерывание");
            }
        });

    }

    public void saveGridToFile(String filePath) {
        controller.saveGrid(filePath, new AbstractCallback() {
            @Override
            void onSuccess() {
                view.showInformMessage("Файл успешно сохранен!", "Сохранение файла");
            }

            @Override
            public void onFail(RuntimeException e) {
                view.showErrorMessage("Ошибка при сохранении файла!", "Сохранение файла");
            }
        });
    }
}