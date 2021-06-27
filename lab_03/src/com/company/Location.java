package com.company;

/**
 * Этот класс представляет собой конкретное место на 2D-карте. Координаты целочисленные значения.
 **/
public class Location {
    public int xCoord;
    public int yCoord;

    /** Создает новое местоположение с указанными целыми координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создает новое местоположение с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            if (xCoord == other.xCoord && yCoord == other.yCoord) {
                return true;
            }
        }
        return false;
    }

    /** Предоставляет хэш-код для каждого местоположения. **/
    public int hashCode() {
        int result = 31;
        result = 31 * result + xCoord;
        result = 31 * result + yCoord;
        return result;
    }
}
