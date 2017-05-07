package model;

/**
 * Created by User on 07.05.2017
 */
public class Cell {
    double value;
    boolean isActive;

    public Cell(double value) {
        isActive = true;
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getValue() {
        return value;
    }

    public boolean isActive() {
        return isActive;
    }
}