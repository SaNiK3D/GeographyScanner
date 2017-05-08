package geographyMap;

/**
 * Created by User on 07.05.2017
 */
public class Cell {
    private double value;
    private boolean isActive;

    public Cell(double value) {
        isActive = true;
        this.value = value;
    }

    public Cell() {
        isActive = false;
        value = 0d;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setActive() {
        isActive = true;
    }

    public double getValue() {
        return value;
    }

    public boolean isActive() {
        return isActive;
    }
}