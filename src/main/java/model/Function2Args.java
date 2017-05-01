package model;

/**
 * Created by User on 24.04.2017
 */
public class Function2Args {
    public final Coordinate coordinate;
    public final int value;

    public Function2Args(int x, int y, int value) {
        coordinate = new Coordinate(x, y);
        this.value = value;
    }

}