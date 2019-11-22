package part1.lesson05.task01;

import java.util.*;
import static part1.lesson05.task01.Pet.PetComparator.*;

/*
Разработать программу – картотеку домашних животных. У каждого животного есть уникальный идентификационный номер, кличка,
 хозяин (объект класс Person с полями – имя, возраст, пол), вес.
Реализовать:
метод добавления животного в общий список (учесть, что добавление дубликатов должно приводить к исключительной ситуации)
поиск животного по его кличке (поиск должен быть эффективным)
изменение данных животного по его идентификатору
вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
 */
/**
 * Каталог домашних животных
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class PetsCatalog {
    private final static int BEGIN_PETS_COUNT = 10000;
    /**
     * Коллекция с домашними животными key- кличка, value - hashset по id животного
     */
    private static Map<String, Map<Long, Pet>> pets;

    private PetsCatalog() {
        pets = new HashMap<>();
    }

    /**
     * Добавление домашнего животного в каталог с проверкой на вхождение
     *
     * @param pet - добавляемое животное
     * @throws Exception - при обнаружении этого животного в каталоге
     */
    private void addPet(Pet pet) throws Exception {
        String name = pet.getName();
        if (pets.containsKey(name)) {//есть животные с такой кличкой
            if (!pets.get(name).containsKey(pet.getId())) {
                pets.get(name).put(pet.getId(), pet);
            } else {
                throw new Exception("Это животное уже есть в каталоге: " + pet.toString());
            }
        } else {
            Map<Long, Pet> newNamePets = new HashMap<>();
            newNamePets.put(pet.getId(), pet);
            pets.put(pet.getName(), newNamePets);
        }
    }

    /**
     * Поиск животных по кличке
     *
     * @param name - кличка
     * @return - список найденных животных
     */
    private Map<Long, Pet> searchPets(String name) {
        return pets.get(name);
    }

    /**
     * Изменение данных животного по его идентификатору
     *
     * @param pet - домашнее животное, на значения полей которого надо поменять данные в каталоге
     * @return - результат редактирования
     */
    private boolean editPet(Pet pet) {
        boolean edited = false;
        for (Map.Entry<String, Map<Long, Pet>> entry : pets.entrySet()) {
            String key = entry.getKey();
            if (pets.get(key).containsKey(pet.getId())) {
                pets.get(key).replace(pet.getId(), pet);
                edited = true;
                break;
            }
        }
        return edited;
    }

    /**
     * Генерируется каталог домашних животных
     */
    private void anyPets() {
        pets.clear();
        for (int i = 0; i < BEGIN_PETS_COUNT; i++) {
            Pet newPet = new Pet(i, RandomGenerateItem.randomName(), new Person(), RandomGenerateItem.randomWeight(0.5, 50, 2));
            try {
                this.addPet(newPet);
            } catch (Exception e) {
                i--;
                System.out.println("При генерации произвольного каталога животных возникла ошибка: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        PetsCatalog pC = new PetsCatalog();
        pC.anyPets();
        String someName = "A";
        System.out.println("Найденные животные с кличкой " + someName + ":");
        Map<Long, Pet> findedPets = pC.searchPets(someName);
        if (findedPets != null)
            for (Map.Entry<Long, Pet> entry : findedPets.entrySet()) {
                Long key = entry.getKey();
                System.out.println(findedPets.get(key).toString());
            }

        if (pC.editPet(new Pet(1, "Рамзес", new Person(33, Person.Sex.WOMAN, "Alina"), 6.55))) {
            System.out.println("Данные о домашнем животном отредактированы.");
        } else {
            System.out.println("Не удалось отредактировать данные.");
        }

        List<Pet> listPets = new ArrayList<>();
        for (Map.Entry<String, Map<Long, Pet>> entry : pets.entrySet()) {
            List<Pet> list = new ArrayList<>(entry.getValue().values());
            listPets.addAll(list);
        }
        listPets.sort(sorting(getComparator(Pet.PetComparator.PERSON_SORT, Pet.PetComparator.NAME_SORT, Pet.PetComparator.WEIGHT_SORT), true));
        listPets.forEach(System.out::println);
    }


}
