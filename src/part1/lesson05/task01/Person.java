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
    Person(int age, Sex sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    Person(){
        this.age = (int)Math.floor(100*Math.random());
        this.sex = randomEnum();
        this.name = RandomGenerateItem.randomName();
    }

    private int getAge() {
        return age;
    }

    private Sex getSex() {
        return sex;
    }

    private String getName() {
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
                "sex=" + sex + ((sex==Sex.MAN)?"  ":"") +
                ", age=" + age + ((age < 10)?"  ":((age != 100)?" ":"")) +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public int compareTo(Person person) {
        if ((this.getSex() == Person.Sex.MAN && person.getSex() == Person.Sex.WOMAN)
                || (sex.equals(person.getSex()) && age > person.getAge())
                || (sex.equals(person.getSex()) && age == person.getAge()
                && name.compareTo(person.getName()) < 0)
        ) {
            return 1;
        }
        return -1;
    }

    /**
     * Cлучайный выбор значения из перечисления
     *
     * @return - случайный выбор из перечисления
     */
    private static Sex randomEnum() {
        int x = new Random().nextInt(Sex.class.getEnumConstants().length);
        return Sex.class.getEnumConstants()[x];
    }

    enum Sex {MAN, WOMAN}
}
