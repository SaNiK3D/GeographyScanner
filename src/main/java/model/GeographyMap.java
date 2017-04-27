package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 23.04.2017
 */
public class GeographyMap {
    private double[][] gridHeights;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int gridStep;

    public GeographyMap(Coordinate[] borderCoordinates, int gridStep) {
        findMinAndMaxCoordinates(borderCoordinates);
        this.gridStep = gridStep;
        makeGrid();

    }

    private void findMinAndMaxCoordinates(Coordinate[] borderCoordinates) {
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        for (Coordinate coordinate : borderCoordinates) {
            minX = Math.min(minX, coordinate.x);
            minY = Math.min(minY, coordinate.y);
            maxX = Math.max(maxX, coordinate.x);
            maxY = Math.max(maxY, coordinate.y);
        }
    }

    private void makeGrid() {
        int sizeX = (maxX - minX) / gridStep;
        int sizeY = (maxY - minY) / gridStep;
        gridHeights = new double[sizeX][sizeY];

    }

    public double[][] interpolate(Function2Args[] functions){
        Map<Point, Double> functionsMap = new HashMap<Point, Double>();
        for (Function2Args fun : functions) {
            functionsMap.put(fun.coordinate, fun.value);
        }
        for (int i = 0; i < gridHeights.length; i++) {
            for (int j = 0; j < gridHeights[i].length; j++) {
                Point currentCoordinate = new Point(i, j);
                if(functionsMap.containsKey(currentCoordinate)){
                    gridHeights[i][j] = functionsMap.get(currentCoordinate);
                }
                else {
                    gridHeights[i][j] = interpolateAtXY(i, j, functions);
                }
            }
        }

        return gridHeights;
    }

    private double interpolateAtXY(int x, int y, Function2Args[] functions){
        double lagrangeXY = 0d;
        for(int n = 0; n < gridHeights.length; n++){
            for(int m = 0; m < gridHeights[n].length; m++){
                double basis = functions[];
                for(int i = 0; i < gridHeights.length; i++){
                    if(i == n) continue;
                    for(int j = 0; j < gridHeights[n].length; j++){
                        if(j == m) continue;
                        basis *= (x - functions[i].coordinate.x)*(y - functions[j].coordinate.y)/
                                (functions[n].coordinate.x - functions[i].coordinate.x)*(functions[m].coordinate.y - functions[j].coordinate.y);
                    }
                }
                lagrangeXY += basis;
            }
        }

        return lagrangeXY;
    }

}
