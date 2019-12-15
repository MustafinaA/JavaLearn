package part1.lesson12.task02;

import part1.lesson09.task01.Worker;
import part1.lesson09.task01.WorkerLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
/*
 * Задание 2.     Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace /Permanent Generation
 */

/**
 * Демонстрация утечки памяти в Permgen
 *
 * @author Alina
 * @version 1.0
 * укажем последовательный сборщик мусора, максим размер metaspase 50 Мб,
 * процессу можно использовать до 100 Мб памяти
 * JVM запустит процесс с 256 МБ памяти и позволит процессу использовать до 2048 Мб памяти
 * -XX:+UseSerialGC -XX:MaxMetaspaceSize=50m -Xms256m -Xmx2048m
 * Exception in thread "main" java.lang.OutOfMemoryError: Metaspace
 */
public class PermanentOutOfMemoryError {
    public static void main(String[] args) {
        List<Worker> list = new ArrayList<>();
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                WorkerLoader wl = new WorkerLoader();
                Class<?> cl = wl.loadClass("part1.lesson09.task01.SomeClass");
                Worker worker = (Worker) cl.getDeclaredConstructor().newInstance();
                list.add(worker);
                if (i % 100 == 0) {
                    Thread.sleep(1);
                    list.remove(0);
                }
            }
        } catch (InstantiationException e) {
            System.out.println("При создании экземпляра Worker");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Ошибка доступа при создании экземпляра");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Исключение цели вызова при создании экземпляра");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("Не может получить объявленный конструктор");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не найден при загрузке класса");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Исключение в методе блокирования потока");
            e.printStackTrace();
        }
    }
}
