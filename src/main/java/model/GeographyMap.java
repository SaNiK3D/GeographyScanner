package model;

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
        if (borderCoordinates.length < 3)
            throw new IllegalArgumentException("Количество координат границ должно быть как минимум 3");
        findMinAndMaxCoordinates(borderCoordinates);
        this.gridStep = gridStep;
        makeGrid(borderCoordinates);

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

    private void makeGrid(Coordinate[] borderCoordinates) {
        int sizeX = ((maxX - minX) / gridStep) + 1;

        int[] columnGridSizes = new int[sizeX];
        Coordinate startLineCoordinate;
        Coordinate endLineCoordinate;
        for (int i = 0; i < borderCoordinates.length; i++) {
            startLineCoordinate = borderCoordinates[i];
            if (i == borderCoordinates.length - 1) {
                endLineCoordinate = borderCoordinates[0];
            } else {
                endLineCoordinate = borderCoordinates[i + 1];
            }

            calculateSizesBetweenStartAndEndCoordinate(startLineCoordinate, endLineCoordinate, columnGridSizes);
        }

        gridHeights = new double[sizeX][];
        for (int i = 0; i < columnGridSizes.length; i++) {
            gridHeights[i] = new double[columnGridSizes[i]];
        }

    }

    private void calculateSizesBetweenStartAndEndCoordinate(Coordinate start, Coordinate end, int[] columnSizes) {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = end.x - start.x;//проекция на ось икс
        dy = end.y - start.y;//проекция на ось игрек

        incx = sign(dx);
    /*
	 * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
	 * справа налево по иксу, то incx будет равен -1.
	 * Это будет использоваться в цикле постороения.
	 */
        incy = sign(dy);
	/*
	 * Аналогично. Если рисуем отрезок снизу вверх -
	 * это будет отрицательный сдвиг для y (иначе - положительный).
	 */

        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|

        if (dx > dy)
        //определяем наклон отрезка:
        {
	 /*
	  * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
	  * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
	  * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
	  * по y сдвиг такой отсутствует.
	  */
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;//тогда в цикле будем двигаться по y
        }

        x = start.x;
        y = start.y;
        err = el / 2;
        columnSizes[0] = (y - minY) / gridStep + 1;

        for (int t = 0; t < el; t++)//идём по всем точкам, начиная со второй и до последней
        {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            } else {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            int sizeIndex = (x - minX) / gridStep + 1;
            int gridY = (y - minY) / gridStep + 1;
            columnSizes[sizeIndex] = Math.max(gridY, columnSizes[sizeIndex]);
        }
    }

    private int sign(int x) {
        return x > 0 ? 1 : -1;
    }

    public double[][] interpolate(Function2Args[] functions) {
        Map<Coordinate, Double> functionsMap = new HashMap<Coordinate, Double>();
        Function2Args functionsInCells[] = new Function2Args[functions.length];
        for (int i = 0; i < functions.length; i++) {
            int gridX = (functions[i].coordinate.x - minX) / gridStep + 1;
            int y = (functions[i].coordinate.y - minY) / gridStep + 1;
            functionsMap.put(new Coordinate(gridX, y), functions[i].value);
            functionsInCells[i] = new Function2Args(gridX, y, functions[i].value);
        }
        for (int i = 0; i < gridHeights.length; i++) {
            for (int j = 0; j < gridHeights[i].length; j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                if (functionsMap.containsKey(currentCoordinate)) {
                    gridHeights[i][j] = functionsMap.get(currentCoordinate);
                } else {
                    gridHeights[i][j] = interpolateAtXY(i, j, functionsInCells);
                }
            }
        }

        return gridHeights;
    }

    private double interpolateAtXY(int x, int y, Function2Args[] functions) {
        double lagrangeXY = 0d;
        for (int n = 0; n < gridHeights.length; n++) {
                double basis = functions[n].value;
                for (int i = 0; i < gridHeights.length; i++) {
                    if (i == n) continue;
                    for (int j = 0; j < gridHeights[n].length; j++) {
                        if (j == n) continue;
                        basis *= (x - functions[i].coordinate.x) * (y - functions[j].coordinate.y) /
                                (functions[n].coordinate.x - functions[i].coordinate.x) * (functions[n].coordinate.y - functions[j].coordinate.y);
                    }
                lagrangeXY += basis;
            }
        }

        return lagrangeXY;
    }

}
