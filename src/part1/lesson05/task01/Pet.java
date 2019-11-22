package part1.lesson05.task01;

import java.util.Comparator;
import java.util.Objects;

/**
 * Класс домашнее животное
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class Pet{
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

    private double getWeight() {
        return weight;
    }
    private Person getPerson() {
        return person;
    }

    /**
     * Перечисление компораторов домашних животных
     */
    public enum PetComparator implements Comparator<Pet> {
        PERSON_SORT {
            public int compare(Pet o1, Pet o2) {
                return o1.getPerson().compareTo(o2.getPerson());
            }},
        NAME_SORT {
            public int compare(Pet o1, Pet o2) {
                return o1.getName().compareTo(o2.getName());
            }},
        WEIGHT_SORT {
            public int compare(Pet o1, Pet o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }};

        /**
         * метод упорядочивания
         * @param other - компоратор
         * @param orderAsc - askending|descending по возрастанию или по убыванию
         * @return - компоратор
         */
        public static Comparator<Pet> sorting(final Comparator<Pet> other, boolean orderAsc) {
            return (o1, o2) -> (orderAsc?1:-1) * other.compare(o1, o2);
        }
        /**
         * Возращает компоратор по входным enum-элементам
         * @param multipleOptions - компораторы домашних животных переменного количества
         * @return - мультикомпоратор
         */
        public static Comparator<Pet> getComparator(final PetComparator... multipleOptions) {
            return (o1, o2) -> {
                for (PetComparator option : multipleOptions) {
                    int result = option.compare(o1, o2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            };
        }
    }
}
