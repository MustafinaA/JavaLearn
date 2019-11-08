package part1.lesson02.task03;

import java.util.Random;

public class Main {
    /**
     * Количество элементов в массиве объектов Person
     */
    final static int personCount = 10000;
    /**
     * алфавит для имен
     */
    final static String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        Person[] personArr = generatePerson(personCount);
        BubbleSort bs = new BubbleSort(personArr);
        long startTime = System.currentTimeMillis();
        try {
            Person[] arrBs = bs.sort();
        } catch (Exception e) {
            System.out.println("При сортировке массива найден объект с идентичными значениями полей age и name: " + e.getMessage());
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTimeBs = stopTime - startTime;
        try {
            MergeSort ms = new MergeSort(personArr);
            Person[] arrMs = new Person[personArr.length];
            long elapsedTimeMs;
            startTime = System.currentTimeMillis();
            try {
                arrMs = ms.sort();
            } catch (Exception e) {
                System.out.println("При сортировке массива найден объект с идентичными значениями полей age и name: " + e.getMessage());
                throw new Exception("Массив не будет отсортирован из-за этого", e);
            } finally {
                stopTime = System.currentTimeMillis();
                elapsedTimeMs = stopTime - startTime;
            }
            for (Person p : arrMs) {
                System.out.println(p.toString());
            }
            System.out.println("Время на сортировку пузырьковым методом: " + elapsedTimeBs + " мс");
            System.out.println("Время на сортировку слиянием: " + elapsedTimeMs + " мс");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод генерации массива Person
     *
     * @param count - количество объектов в массиве
     * @return массив Person
     */
    private static Person[] generatePerson(int count) {
        Person[] result = new Person[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Person(
                    (int)Math.floor(100*Math.random()),
//                    (int) (100.0 * Math.random()),
                    randomEnum(Person.Sex.class),
                    randomName());
        }
        return result;
    }

    /**
     * Генерирует случайное "имя" - набор до 10 букв алфавита
     *
     * @return - имя
     */
    public static String randomName() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = new Random().nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(new Random().nextInt(lexicon.length())));
            }
        }
        return builder.toString();
    }

    /**
     * Cлучайный выбор значения из перечисления
     *
     * @param clazz - класс перечисления
     * @return - случайный выбор из перечисления
     */
    public static Person.Sex randomEnum(Class<Person.Sex> clazz) {
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
