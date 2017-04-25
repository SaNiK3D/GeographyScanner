package model;

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
        for(int n = 0; n < )
    }

}
