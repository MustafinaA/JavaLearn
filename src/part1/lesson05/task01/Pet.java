package part1.lesson05.task01;

import java.util.Objects;

/**
 * Класс домашнее животное
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class Pet implements Comparable<Pet> {
    /**
     * идентификатор домашнего животного
     */
    private long id;
    /**
     * кличка
     */
    private String name;
    /**
     * хозяин
     */
    private Person person;
    /**
     * вес
     */
    private double weight;

    Pet(long id, String name, Person person, double weight) {
        this.id = id;
        this.name = name;
        this.person = person;
        this.weight = weight;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", person=" + person.toString() +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (id != pet.id) return false;
        if (Double.compare(pet.weight, weight) != 0) return false;
        if (!Objects.equals(name, pet.name)) return false;
        return Objects.equals(person, pet.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, person,weight);
    }

    long getId() {
        return id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setPerson(Person person) {
        this.person = person;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }

    private double getWeight() {
        return weight;
    }
    private Person getPerson() {
        return person;
    }

    @Override
    public int compareTo(Pet pet) {
        if ((!this.getPerson().equals(pet.getPerson()) && this.getPerson().compareTo(pet.getPerson())< 0)
                || (this.getPerson().equals(pet.getPerson()) && this.getName().compareTo(pet.getName()) > 0)
                || (this.getPerson().equals(pet.getPerson()) && this.getName().equals(pet.getName()) && this.getWeight() > pet.getWeight())) {
            return 1;
        }
        return -1;
    }


}
