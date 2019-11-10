package part1.lesson03.task02;

import java.util.Collection;
import java.util.Iterator;

/*Создать класс ObjectBox, который будет хранить коллекцию Object.
У класса должен быть метод addObject, добавляющий объект в коллекцию.
У класса должен быть метод deleteObject, проверяющий наличие объекта в коллекции и при наличии удаляющий его.
Должен быть метод dump, выводящий содержимое коллекции в строку.*/
public class ObjectBox<T extends Collection> {
    T collection;

    ObjectBox(T collection) {
        this.collection = collection;
    }

    public void setCollection(T collection) {
        collection = collection;
    }

    /**
     * Добавление объекта в коллекцию
     *
     * @param o - объект для добавления
     */
    public void addObject(Object o) {
        collection.add(o);
    }

    /**
     * Проверяет наличие объекта в коллекции и при наличии удаляет его
     *
     * @param o - объект для удаления
     */
    public void deleteObject(Object o) {
        collection.remove(o);
    }

    /**
     * Выводит содержимое коллекции в строку
     *
     * @return - коллекция в строковом представлении
     */
    public String dump() {
        StringBuilder sb = new StringBuilder();
        Iterator i = collection.iterator();
        while (i.hasNext()) {
            sb.append(i.next());
        }
        return sb.toString();
    }
}
