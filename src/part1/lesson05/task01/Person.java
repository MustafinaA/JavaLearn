package part1.lesson05.task01;

import java.util.Objects;
import java.util.Random;

/**
 * Класс, описывающий хозяина питомца
 *
 * @author Алина Мустафина
 * @version 1.0
 * Класс Person характеризуется полями age (возраст, целое число 0-100),
 * sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN),
 * name (имя - строка)
 */
public class Person implements Comparable<Person>{

    /**
     * Возраст, целое число 0-100
     */
    private int age;
    /**
     * Пол
     */
    private Sex sex;
    /**
     * Имя
     */
    private String name;

    /**
     * Конструктор
     * @param age - возраст
     * @param sex - пол
     * @param name - имя
     */
    public Person(int age, Sex sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    public Person(){
        this.age = (int)Math.floor(100*Math.random());
        this.sex = randomEnum(Person.Sex.class);
        this.name = RandomGenerateItem.randomName();
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                sex == person.sex &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, sex, name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "sex=" + sex +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Person person) {
        if ((this.getSex() == Person.Sex.MAN && person.getSex() == Person.Sex.WOMAN)
                || (this.getSex().equals(person.getSex()) && this.getAge() > person.getAge())
                || (this.getSex().equals(person.getSex()) && this.getAge() == person.getAge()
                && this.getName().compareTo(person.getName()) < 0)
        ) {
            return 1;
        }
        return -1;
    }

    /**
     * Cлучайный выбор значения из перечисления
     *
     * @param clazz - класс перечисления
     * @return - случайный выбор из перечисления
     */
    public static Sex randomEnum(Class<Sex> clazz) {
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    static enum Sex {MAN, WOMAN}
}
