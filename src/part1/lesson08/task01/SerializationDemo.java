package part1.lesson08.task01;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
Необходимо разработать класс, реализующий следующие методы:
void serialize (Object object, String file);
Object deSerialize(String file);
Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
Обязательна сериализация и десериализация "плоских" объектов (все поля объекта - примитивы, или String).
 */

/**
 * Сериализация и десериализация объект - файл на основе рефлексии
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class SerializationDemo {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        Person person = new Person("Иван", 1999, 987.78, 'И');
        System.out.println("Object before serialization  => " + person.toString());
        serialize(person, "nnn.txt");
        System.out.println("Object after deserialization  => " + deSerialize("nnn.txt").toString());
    }

    /**
     * Сериализация объекта Object в файл file
     *
     * @param object - сериализуемый объект
     * @param file   - имя файла, за записи сеарилизуемого объекта
     */
    private static void serialize(Object object, String file) throws IOException, IllegalAccessException {
        new File(file);
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append("{");// записываю в json
        int fieldNum = 0;
        for (Field declaredField : fields) {
            declaredField.setAccessible(true); // доступ к приватному полю
            builder.append('\"').append(declaredField.getName()).append("\":");
            if (!declaredField.getType().isPrimitive()) {
                builder.append("\"").append(declaredField.get(object)).append('\"');
            } else if (declaredField.getType() == Character.TYPE) {
                builder.append("\'").append(declaredField.get(object)).append('\'');
            } else {
                builder.append(declaredField.get(object));
            }
            if (++fieldNum < fields.length) {
                builder.append(',');
            }
        }
        builder.append("}");
        try (FileOutputStream fos = new FileOutputStream(file);
             Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            out.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Десериализация объекта из файла
     *
     * @param file - имя файла, для чтения десериализуемого объекта
     * @return - объект после десериализации
     */
    private static Object deSerialize(String file) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Object object = new Person();
        Scanner input = new Scanner(new File(file));
        input.useDelimiter(",");
        while (input.hasNext()) {
            String jsonItem = input.next();
            String[] item = jsonItem.split(":");
            String fieldName = item[0].substring(item[0].indexOf("\"") + 1, item[0].lastIndexOf("\""));
            String fieldValue = item[1].substring(0, item[1].charAt(item[1].length() - 1) == '}' ? item[1].length() - 1 : item[1].length());
            Field tempField = object.getClass().getDeclaredField(fieldName);
            tempField.setAccessible(true);
            if (tempField.getType().isPrimitive()) {
                if (tempField.getType() == Character.TYPE) {
                    tempField.setChar(object, fieldValue.replaceAll("\'", "").charAt(0));
                } else if (tempField.getType() == Integer.TYPE) {
                    tempField.setInt(object, Integer.parseInt(fieldValue));
                } else if (tempField.getType() == Double.TYPE) {
                    tempField.setDouble(object, Double.parseDouble(fieldValue));
                }
            } else {
                tempField.set(object, fieldValue.replaceAll("\"", ""));
            }
        }
        return object;
    }
}
