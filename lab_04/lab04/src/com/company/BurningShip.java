package com.company;

import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator{
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    public int numIterations(double x, double y) {
        int iteration = 0;
        double zreal = 0;
        double zimaginary = 0;

        while (iteration < MAX_ITERATIONS && zreal * zreal + zimaginary * zimaginary < 4) {
            double aznach = zreal * zreal - zimaginary * zimaginary + x;
            zimaginary = 2 * Math.abs(zreal) * Math.abs(zimaginary) + y;
            zreal = aznach;
            iteration++;
        }

        if (iteration == MAX_ITERATIONS){
            return -1;
        }
        return iteration;
    }
    @Override
    public String toString() {
        return "BurningShip";
    }

}
