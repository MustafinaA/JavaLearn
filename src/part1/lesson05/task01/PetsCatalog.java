package part1.lesson05.task01;

import java.util.*;

/*
Разработать программу – картотеку домашних животных. У каждого животного есть уникальный идентификационный номер, кличка,
 хозяин (объект класс Person с полями – имя, возраст, пол), вес.
Реализовать:
метод добавления животного в общий список (учесть, что добавление дубликатов должно приводить к исключительной ситуации)
поиск животного по его кличке (поиск должен быть эффективным)
изменение данных животного по его идентификатору
вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
 */
public class PetsCatalog {
    /**
     * Коллекция с домашними животными
     */
    private Set<Pet> pets;

    PetsCatalog(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void addPet(Pet pet) throws Exception {
        if (!pets.contains(pet)) {
            pets.add(pet);
        } else {
            throw new Exception("Это животное уже есть в каталоге: " + pet.toString());
        }
    }

    /**
     * Поиск животных по кличке O(N)
     * (вернется множество совпадений, т.к. возврат первого объекта, удовлетворяющего условию, будет некорректно)
     *
     * @param name - кличка
     * @return - список найденных животных
     */
    public Set<Pet> searchPets(String name) {
        Set<Pet> result = new HashSet<>();
        for (Pet p : pets) {
            if (name.equals(p.getName())) {
                result.add(p);
            }
        }
        return result;
    }

    /**
     * Изменение данных животного по его идентификатору
     *
     * @param id - идентификационный номер редактируемого животного
     * @return - результат редактирования
     */
    public boolean editPet(long id, String name, Person person, double weight) {
        Pet editItem = null;
        for (Pet p : pets) {
            if (id == p.getId()) {
                editItem = p;
                break;
            }
        }
        if (editItem != null) {
            editItem.setName(name);
            editItem.setPerson(person);
            editItem.setWeight(weight);
            return true;
        }
        return false;
    }

    /**
     * Генерируемый Set из N экземпляров Pet
     *
     * @param N - необходимое количество домашних животных
     * @return - коллекция рандомных домашних животных
     */
    public static Set<Pet> anyPetsSet(int N) {
        Set<Pet> result = new HashSet<>();
        for (int i = 0; i < N; i++) {
            result.add(new Pet(i, RandomGenerateItem.randomName(), new Person(), RandomGenerateItem.randomWeight(0.5, 50, 2)));
        }
        return result;
    }

    public static void main(String[] args) {
        PetsCatalog pC = new PetsCatalog(anyPetsSet(100));
        String someName = "A";
        System.out.println("Найденные животные с кличкой " + someName + ":");
        Set<Pet> findedPets = pC.searchPets(someName);
        findedPets.forEach(System.out::println);

        findedPets.forEach(pet -> {
            try {
                pC.addPet(pet);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        if (pC.editPet(1, "Рамзес", new Person(33, Person.Sex.WOMAN, "Alina"), 6.55)) {
            System.out.println("Данные о домашнем животном отредактированы.");
        } else {
            System.out.println("Не удалось отредактировать данные.");
        }

        TreeSet<Pet> treePet = new TreeSet<>();
        for (Pet p : pC.getPets()) {
            treePet.add(p);
        }
        System.out.println("Список животных в отсортированном порядке:");
        for (Pet p : treePet) {
            System.out.println(p.toString());
        }


    }


}
