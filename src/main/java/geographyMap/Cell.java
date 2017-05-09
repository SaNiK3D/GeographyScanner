package geographyMap;

/**
 * Created by User on 07.05.2017
 */
public class Cell {
    private int value;
    private boolean isActive;

    public Cell(int value) {
        isActive = true;
        this.value = value;
    }

    public Cell() {
        isActive = false;
        value = 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setActive() {
        isActive = true;
    }

    public int getValue() {
        return value;
    }

    public boolean isActive() {
        return isActive;
    }
}