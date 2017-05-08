package geographyMap.controller;

import geographyMap.Coordinate;
import geographyMap.Function2Args;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by User on 08.05.2017
 */
class CoordinatesLoader {//todo: сделать проверку на корректность файла
    private static final int DEL = Integer.parseInt(";");

    static Coordinate[] getBorderCoordinates(String filePath) throws IOException {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StreamTokenizer tok = new StreamTokenizer(reader);
        tok.whitespaceChars(DEL, DEL);
        while ((tok.nextToken()) != StreamTokenizer.TT_EOF){
            Coordinate coordinate = readCoordinate(tok);
            coordinates.add(coordinate);
        }

        return coordinates.toArray(new Coordinate[coordinates.size()]);
    }

    private static Coordinate readCoordinate(StreamTokenizer tok) throws IOException {
        int x = readNextInt(tok);
        int y = readNextInt(tok);

        return new Coordinate(x, y);
    }

    private static int readNextInt(StreamTokenizer tok) throws IOException {
        tok.nextToken();
        if(tok.ttype == StreamTokenizer.TT_NUMBER){
            return  (int) tok.nval;
        }
        else {
            throw new WrongFileDataException("Неверный формат данных");
        }
    }

    static Function2Args[] getHeights(String filePath) throws IOException {
        ArrayList<Function2Args> functions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StreamTokenizer tok = new StreamTokenizer(reader);
        tok.whitespaceChars(DEL, DEL);
        while (tok.nextToken() != StreamTokenizer.TT_EOF){
            Function2Args function = readFunction(tok);
            functions.add(function);
        }

        return functions.toArray(new Function2Args[functions.size()]);
    }

    private static Function2Args readFunction(StreamTokenizer tok) throws IOException {
        int x = readNextInt(tok);
        int y = readNextInt(tok);
        int value = readNextInt(tok);

        return new Function2Args(x, y, value);
    }
}
