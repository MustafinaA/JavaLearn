package part1.lesson08.task01;

import jdk.dynalink.linker.support.TypeUtilities;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
 * т.к. не было указано каким образом хранится информация в файле, я выбрала способ построчной записи информации об объекте
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class SerializationDemo {
    /**
     * Установленная в настройках системы строка разрыва строк
     */
    private final static String SEPARATE = System.getProperty("line.separator");
    /**
     * "Символ" экранирования
     */
    private final static String ESC_CHR = "%0";

    public static void main(String[] args) throws IllegalAccessException {
//        Person person = new Person("Иван", 1999, 987.78, 'И');
        Person person = new Person("", 1999, 987.78, 'И');
        System.out.println("Object before serialization  => " + person.toString());
        serialize(person, "primitiveObj.bin");
        System.out.println("Object after deserialization  => " + deSerialize("primitiveObj.bin").toString());
    }

    /**
     * Сериализация объекта Object в файл file
     *
     * @param object - сериализуемый объект
     * @param file   - имя файла, за записи сеарилизуемого объекта
     */
    private static void serialize(Object object, String file) throws IllegalAccessException {
        Class aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        File f = new File(file);
        try (FileWriter writer = new FileWriter(f, StandardCharsets.UTF_8)) {
            writer.write(aClass.getName() + SEPARATE);// запишу информацию о классе
            for (Field fld : fields) {
                fld.setAccessible(true); // доступ к приватному полю
                Class clazz = fld.getType();
                String typeName = clazz.getTypeName();
                //для каждого поля записываем в файл его имя тип и значение для текущего объекта
                writer.write(fld.getName() + SEPARATE);
                writer.write(typeName + SEPARATE);
                writer.write(ESC_CHR + fld.get(object) + ESC_CHR + SEPARATE);
            }
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
    private static Object deSerialize(String file) {
        Class<?> c = null;
        Object obj = null;
        Field tempField = null;
        Class<?> tempType = null;
        String typeName = null;
        Scanner input;
        try {
            input = new Scanner(new File(file));

            while (input.hasNext()) {
                if (c == null) {// следующая строка в файле информация о классе
                    c = Class.forName(input.next());
                    obj = c.getDeclaredConstructor().newInstance();//Constructor.c.newInstance();
                } else {// все остальное относится к филдам
                    if (tempField == null) {
                        String fieldName = input.next();
                        tempField = obj.getClass().getDeclaredField(fieldName);
                        tempField.setAccessible(true);
                    } else if (tempType == null) {
                        typeName = input.next();
                        if (typeName.equals("java.lang.String")) {
                            tempType = Class.forName(typeName);
                        } else {
                            tempType = TypeUtilities.getPrimitiveTypeByName(typeName);
                        }
                    } else {
                        String inputNext = input.next();
                        int formIndex = inputNext.indexOf(ESC_CHR) + ESC_CHR.length();
                        int toIndex = inputNext.indexOf(ESC_CHR, ESC_CHR.length() - 1);
                        String strValue = inputNext.substring(formIndex, toIndex);
                        if (tempField.getType().isPrimitive()) {
                            // т.к. неизвестно какие типы полей есть у этого класса, то перебираем все
                            if (tempField.getType().equals(Character.TYPE)) {
                                tempField.setChar(obj, strValue.charAt(0));
                            } else if (tempField.getType().equals(Integer.TYPE)) {
                                tempField.setInt(obj, Integer.parseInt(strValue));
                            } else if (tempField.getType().equals(Double.TYPE)) {
                                tempField.setDouble(obj, Double.parseDouble(strValue));
                            } else if (tempField.getType().equals(Byte.TYPE)) {
                                tempField.setByte(obj, Byte.parseByte(strValue));
                            } else if (tempField.getType().equals(Short.TYPE)) {
                                tempField.setShort(obj, Short.parseShort(strValue));
                            } else if (tempField.getType().equals(Long.TYPE)) {
                                tempField.setLong(obj, Long.parseLong(strValue));
                            } else if (tempField.getType().equals(Float.TYPE)) {
                                tempField.setFloat(obj, Float.parseFloat(strValue));
                            } else if (tempField.getType().equals(Boolean.TYPE)) {
                                tempField.setBoolean(obj, Boolean.parseBoolean(strValue));
                            }
                        } else if (tempField.getType().equals(Class.forName("java.lang.String"))) {
                            if (strValue.length() > 0) {
                                tempField.set(obj, strValue);
                            } else {
                                tempField.set(obj, "");
                            }
                        } else {
                            throw new IllegalArgumentException("Not primitive or String type : " + tempField.getType());
                        }
                        tempField = null;
                        tempType = null;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден : " + file);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Тип не найден : " + typeName);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Не удалось установить значение в поле : " + tempField);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Не удалось создать экземпляр класса : " + c);
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("Не найдено поле");
            e.printStackTrace();
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
