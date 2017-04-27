package model;

import java.awt.*;

/**
 * Created by User on 24.04.2017
 */
public class Function2Args {
    public final Point coordinate;
    public final double value;

    public Function2Args(int x, int y, double value) {
        coordinate = new Point(x, y);
        this.value = value;
    }

}