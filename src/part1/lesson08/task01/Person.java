package part1.lesson08.task01;

import java.io.Serializable;

/**
 * "плоский" объект  (все поля объекта - примитивы, или String).
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class Person implements Serializable {

    private String name;
    private int year;
    private double money;
    private char letter;

    Person() {
        this.name = "";
        this.year = 1970;
        this.money = 0.0;
        this.letter = '-';
    }

    Person(String name, int year, double money, char letter) {
        this.name = name;
        this.year = year;
        this.money = money;
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", money=" + money +
                ", letter=" + letter +
                '}';
    }
}
