package part1.lesson09.task01;
/*
Программа с консоли построчно считывает код метода doWork. Код не должен требовать импорта дополнительных классов.
После ввода пустой строки считывание прекращается и считанные строки добавляются в тело метода public void doWork() в файле SomeClass.java.
Файл SomeClass.java компилируется программой (в рантайме) в файл SomeClass.class.
Полученный файл подгружается в программу с помощью кастомного загрузчика
Метод, введенный с консоли, исполняется в рантайме (вызывается у экземпляра объекта подгруженного класса)
 */

/**
 * @author Алина Мустафина
 * @version 1.0
 */
public class ClassLoaderDemo {
    public static void main(String[] args) {
        GenerateClassFile gcf = new GenerateClassFile();
        if (gcf.getStatus() == 0) {
            executeWorker();
        }
    }

    /**
     * Использование кастомного загрузчика для класса, имплементирующего Worker
     */
    public static void executeWorker() {
        try {
            ClassLoader loader = new WorkerLoader();
            Class<?> cl = loader.loadClass("part1.lesson09.task01.SomeClass");
            Worker worker = (Worker) cl.newInstance();//создаем экземпляр объекта подгруженного класса
            worker.doWork();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
