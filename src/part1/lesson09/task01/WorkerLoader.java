package part1.lesson09.task01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Кастомный ClassLoader для класса, имплементирующего Worker
 * должны переопределить 2 метода findClass, loadClass
 */
public class WorkerLoader extends ClassLoader {
    /**
     * Путь до скомпилированного файла
     */
    private final String PATH = "src/part1/lesson09/task01/SomeClass.class";

    /**
     * Поиск класса по имени
     *
     * @param name имя класса
     * @return class
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(PATH));
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * загрузка
     *
     * @param name имя класса
     * @return class
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if ("part1.lesson09.task01.SomeClass".equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }
}
