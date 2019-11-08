package part1.lesson02.task03;

import java.util.Objects;

/**
 * Класс, описывающий человека
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
                "age=" + age +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Person person) {
        if (this.getAge() == person.getAge() && this.getName().equals(person.getName())) {
            try {
                throw new Exception(this.toString());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        if ((this.getSex() == Person.Sex.MAN && person.getSex() == Person.Sex.WOMAN)
                || (this.getSex().equals(person.getSex()) && this.getAge() > person.getAge())
                || (this.getSex().equals(person.getSex()) && this.getAge() == person.getAge()
                && this.getName().compareTo(person.getName()) < 0)
        ) {
            return 1;
        }
        return -1;
    }

    static enum Sex {MAN, WOMAN}
}
