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

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public double getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMoney(double money) {
        this.money = money;
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
