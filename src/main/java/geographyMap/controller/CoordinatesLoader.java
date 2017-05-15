package geographyMap.controller;

import geographyMap.Cell;
import geographyMap.Coordinate;
import geographyMap.Function2Args;
import geographyMap.Grid;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by User on 08.05.2017
 */
class CoordinatesLoader {//todo: сделать проверку на корректность файла
    private static final char DEL = ';';

    static Coordinate[] getBorderCoordinates(String filePath) throws IOException {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StreamTokenizer tok = new StreamTokenizer(reader);
        tok.whitespaceChars(DEL, DEL);
        while ((tok.nextToken()) != StreamTokenizer.TT_EOF){
            Coordinate coordinate = readCoordinate(tok);
            coordinates.add(coordinate);
        }

        reader.close();
        return coordinates.toArray(new Coordinate[coordinates.size()]);
    }

    private static Coordinate readCoordinate(StreamTokenizer tok) throws IOException {
        int x = getNextInt(tok);
        tok.nextToken();
        int y = getNextInt(tok);

        return new Coordinate(x, y);
    }

    private static int getNextInt(StreamTokenizer tok) throws IOException {
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
        tok.whitespaceChars(DEL,DEL);
        while (tok.nextToken() != StreamTokenizer.TT_EOF){
            Function2Args function = readFunction(tok);
            functions.add(function);
        }

        reader.close();
        return functions.toArray(new Function2Args[functions.size()]);
    }

    private static Function2Args readFunction(StreamTokenizer tok) throws IOException {
        int x = getNextInt(tok);
        tok.nextToken();
        int y = getNextInt(tok);
        tok.nextToken();
        int value = getNextInt(tok);

        return new Function2Args(x, y, value);
    }

    static void saveGridToFile(Grid grid, String filePath) throws IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath + ".csv"), "Cp866"));
        Cell[][] heights = grid.getHeights();
        writer.println(grid.getStep());
        writer.println(grid.getMinX());
        writer.println(grid.getMinY());
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length - 1; j++) {
                if(heights[i][j].isActive())
                    writer.print(heights[i][j].getValue());
                writer.print(DEL);
            }
            if(heights[i][heights[i].length - 1].isActive())
                writer.print(heights[i][heights[i].length - 1].getValue());
            writer.println();
        }

        writer.close();
    }
}
