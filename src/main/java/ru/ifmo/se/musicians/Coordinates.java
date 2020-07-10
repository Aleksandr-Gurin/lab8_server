package ru.ifmo.se.musicians;

import java.io.Serializable;
import java.util.Objects;

/**
 * Coordinates
 */
public class Coordinates implements Serializable {
    private long x; //Максимальное значение поля: 913
    private double y; //Значение поля должно быть больше -224

    /**
     * Constructor Coordinates
     */
    public Coordinates(){};

    /**
     * Constructor Coordinates
     * @param x x меньше 913
     * @param y y больше -224
     */
    public Coordinates(long x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает y
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает x
     * @return x
     */
    public long getX() {
        return x;
    }

    /**
     * Устанавливает x
     * @param  x new x
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Устанавливает y
     * @param  y new y
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates testClass = (Coordinates) o;
        return x == testClass.x &&
                y == testClass.y;
    }

    @Override
    public String toString() {
        return "x: " + getX() + ", y: " +getY();
    }
}
