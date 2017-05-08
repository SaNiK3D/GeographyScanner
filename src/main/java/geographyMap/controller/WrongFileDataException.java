package geographyMap.controller;

import java.io.IOException;

/**
 * Created by User on 08.05.2017
 */
public class WrongFileDataException extends IOException {
    public WrongFileDataException(String message) {
        super(message);
    }
}
