package geographyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 23.04.2017
 */
public class GeographyMap {
    private Grid grid;

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
        int width = ((maxX - minX) / gridStep) + 1;
        int height = ((maxY - minY) / gridStep) + 1;
        grid = new Grid(width, height);

        Coordinate startLineCoordinate;
        Coordinate endLineCoordinate;
        for (int i = 0; i < borderCoordinates.length; i++) {
            startLineCoordinate = borderCoordinates[i];
            if (i == borderCoordinates.length - 1) {
                endLineCoordinate = borderCoordinates[0];
            } else {
                endLineCoordinate = borderCoordinates[i + 1];
            }

            calculateSizesBetweenStartAndEndCoordinate(startLineCoordinate, endLineCoordinate);
        }


    }

    private void calculateSizesBetweenStartAndEndCoordinate(Coordinate start, Coordinate end) {
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

        int gridX = (x - minX) / gridStep;
        int gridY = (y - minY) / gridStep + 1;
        grid.getHeights()[gridX][gridY].setActive();

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

            gridX = (x - minX) / gridStep;
            gridY = (y - minY) / gridStep + 1;
            grid.getHeights()[gridX][gridY].setActive();
        }
    }

    private int sign(int x) {
        return x > 0 ? 1 : -1;
    }

    public Grid interpolate(Function2Args[] functions) {
        Function2Args functionsInCells[] = new Function2Args[functions.length];
        Map<Coordinate, Double> functionMap = new HashMap<>();
        Map<Coordinate, Integer> functionCount = new HashMap<>();
        for (int i = 0; i < functions.length; i++) {
            int gridX = (functions[i].coordinate.x - minX) / gridStep;
            int gridY = (functions[i].coordinate.y - minY) / gridStep + 1;

            functionsInCells[i] = new Function2Args(gridX, gridY, functions[i].value);

            Coordinate c = new Coordinate(gridX, gridY);
            if(!functionCount.containsKey(c)) {
                functionMap.put(c, functions[i].value);
                functionCount.put(c, 1);
            } else {
                int count = functionCount.get(c);
                count++;
                functionMap.put(c, (functions[i].value + functionMap.get(c)) / count );
                functionCount.put(c, count);
            }
        }
        for (int i = 0; i < grid.getHeights().length; i++) {
            for (int j = 0; j < grid.getHeights()[i].length; j++) {
                if (functionMap.containsKey(new Coordinate(i, j)))
                    grid.getHeights()[i][j].setValue(functionMap.get(new Coordinate(i, j)));
                else
                    grid.getHeights()[i][j].setValue(interpolateAtXY(i, j, functionsInCells));
            }
        }

        return grid;
    }

    private double interpolateAtXY(int x, int y, Function2Args[] functions) {
        double numerator = 0d;
        double denominator = 0d;
        for (Function2Args f : functions) {
            double weight = 1 / Math.pow(distance(x, y, f.coordinate.x, f.coordinate.y), 4);

            denominator += weight;
            numerator += weight * f.value;
        }

        return numerator / denominator;
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static void main(String[] args) {

    }
}
