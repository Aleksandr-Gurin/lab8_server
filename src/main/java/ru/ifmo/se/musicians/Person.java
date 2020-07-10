package ru.ifmo.se.musicians;

import java.io.Serializable;
import java.util.Objects;

/**
 * Человек
 */
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Double height; //Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null

    public Person(){};

    /**
     * Конструктор человека
     * @param name имя человек, не может быть null, Строка не может быть пустой
     * @param height рост человека, не может быть null, Значение поля должно быть больше 0
     * @param eyeColor цвет глаз, может быть null
     * @param hairColor цвет волос, может быть null
     * @param nationality национальность, может быть null
     */
    public Person(String name, Double height, Color eyeColor, Color hairColor, Country nationality){
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor =hairColor;
        this.nationality = nationality;
    }
    public String getName() {
        return name;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Double getHeight() {
        return height;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person testClass = (Person) o;
        return height == testClass.height &&
                Objects.equals(name, testClass.name) &&
                Objects.equals(eyeColor, testClass.eyeColor) &&
                Objects.equals(hairColor, testClass.hairColor) &&
                Objects.equals(nationality, testClass.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height,name,eyeColor,hairColor,nationality);
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Height: " + getHeight() + ", eye color: " + getEyeColor() + ", hair color: " + getHairColor() + ", nationality: " + getNationality();
    }
}
