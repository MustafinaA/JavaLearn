package part1.lesson12.task01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Задание 1.     Необходимо создать программу, которая продемонстрирует утечку памяти в Java.
 * При этом объекты должны не только создаваться, но и периодически частично удаляться,
 * чтобы GC имел возможность очищать часть памяти.
 * Через некоторое время программа должна завершиться с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 */

/**
 * Демонстрация утечки памяти
 *
 * @author Alina
 * @version 1.0
 * укажем сборщик последовательный, позволим процессу использовать до 100 Мб памяти
 * -XX:+UseSerialGC -Xmx100m
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class JavaHeapSpace {

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            list.add("" + (new Random()).nextInt());
            if (i % 100 == 0) {
                Thread.sleep(1);
                list.remove(0);
            }
        }
    }
}
