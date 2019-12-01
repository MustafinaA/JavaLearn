package part1.lesson08.task02;
/*
Задание 2. Предусмотреть работу c любыми типами полей (полями могут быть ссылочные типы).
 */

import jdk.dynalink.linker.support.TypeUtilities;
import sun.reflect.ReflectionFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private final static String SP = System.getProperty("line.separator");

    public static void main(String[] args) throws IllegalAccessException {

        Person person = new Person("Иван", new Company("Alpha","Russia, Kazan, Mira st. 51"));
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
            writer.write(aClass.getName() + SP);// запишу информацию о классе
            writeFieldsInfo(object, fields, writer);// о полях

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Процедура рекурсивной записи в файл информации о полях и значениях, установленных для текущего объекта
     * @param object - объект для парса
     * @param fields - все поля этого объекта
     * @param writer - FileWriter
     * @throws IOException
     * @throws IllegalAccessException
     */
    private static void writeFieldsInfo(Object object, Field[] fields, FileWriter writer) throws IOException, IllegalAccessException {
        for (Field fld : fields) {
            fld.setAccessible(true); // доступ к приватному полю
            Class clazz = fld.getType();
            String typeName = clazz.getTypeName();
            //для каждого поля записываем в файл его имя тип
            writer.write(fld.getName() + SP);
            writer.write(typeName + SP);
            if(clazz.isPrimitive()) {
                writer.write(fld.get(object) + SP);// и значение для текущего объекта
            } else if(clazz.isArray()){// ссылочный тип
                displayArray(clazz.getComponentType(), fld.get(object));
            }else{
//                boolean isPrimitiveOrWrapped = isWrapperType(clazz);
                Object attachedObj = fld.get(object);// значение текущего объекта надо тоже расписать
//                object.getClass().getConstructors();
                writeFieldsInfo(attachedObj, attachedObj.getClass().getDeclaredFields(), writer);
            }
        }
    }

    private static void displayArray(Class arrayType, Object theArray) {
        int length =  java.lang.reflect.Array.getLength(theArray);
        if(arrayType.isArray())  { //многомерный?
            System.out.print("{");
            for(int j = 0; j < length; j++) {
                Object arr2 = java.lang.reflect.Array.get(theArray, j);
                displayArray(arrayType.getComponentType(),arr2);
                if(j!= length-1)
                    System.out.print(",");
            }
            System.out.print("}");
        } else {// одномерный массив
            System.out.print("{");
            for(int j = 0; j < length; j++) {
                System.out.print(java.lang.reflect.Array.get(theArray, j).toString());
                if(j!= length-1)
                    System.out.print(",");
            }
            System.out.print("}");
        }
    }

    private static Object deSerialize(String file) {
        Class<?> c = null;
        Object obj = null;
        Field tempField = null;
        Class<?> tempType = null;
        String typeName = null;
        Scanner input;
        try {
            input = new Scanner(new File(file));
            readFieldInfo(c, obj, tempField, tempType, input);
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static void readFieldInfo(Class<?> clazz, Object obj, Field tempField, Class<?>  tempType, Scanner input) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Object obj_ = obj;
        while (input.hasNext()) {
            if (clazz == null) {// следующая строка в файле информация о классе
                clazz = Class.forName(input.next());
                obj_ =  instance(clazz);//InstantiationException  на obj_ = clazz.newInstance();
            } else {// все остальное относится к филдам
                if (tempField == null) {
                    tempField = obj_.getClass().getDeclaredField(input.next());
                    tempField.setAccessible(true);
                } else if (tempType == null) {
                    String typeName = input.next();
                    if (Class.forName(typeName).isPrimitive()) {
                        tempType = TypeUtilities.getPrimitiveTypeByName(typeName);
                    } else {
                        tempType = Class.forName(typeName);
                    }
                } else {
                    if (tempField.getType().isPrimitive()) {
                        if (tempField.getType().equals(Character.TYPE)) {// т.к. мы неизвестно какие типы полей есть у этого класса, то перебираем все
                            tempField.setChar(obj, input.next().charAt(0));
                        } else if (tempField.getType().equals(Integer.TYPE)) {
                            tempField.setInt(obj, Integer.parseInt(input.next()));
                        } else if (tempField.getType().equals(Double.TYPE)) {
                            tempField.setDouble(obj, Double.parseDouble(input.next()));
                        } else if (tempField.getType().equals(Byte.TYPE)) {
                            tempField.setByte(obj, Byte.parseByte(input.next()));
                        } else if (tempField.getType().equals(Short.TYPE)) {
                            tempField.setShort(obj, Short.parseShort(input.next()));
                        } else if (tempField.getType().equals(Long.TYPE)) {
                            tempField.setLong(obj, Long.parseLong(input.next()));
                        } else if (tempField.getType().equals(Float.TYPE)) {
                            tempField.setFloat(obj, Float.parseFloat(input.next()));
                        } else if (tempField.getType().equals(Boolean.TYPE)) {
                            tempField.setBoolean(obj, Boolean.parseBoolean(input.next()));
                        }
                    } else {
                        Object attachedObj = tempType.newInstance();
                        Field attachedField = null;
                        Class<?>  attachedType = null;
                        readFieldInfo(clazz, attachedObj, attachedField, attachedType, input);
                        //tempField.set(obj, input.next());
                    }
                    tempField = null;
                    tempType = null;
                }
            }
        }
    }

    /**
     * Универсальный вызов конструктора
     * @param c
     * @param <T>
     * @return
     */
    static <T> T instance(Class<T> c) {
        try {
            ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();

            @SuppressWarnings("unchecked")
            Constructor<T> constructor = (Constructor<T>) reflectionFactory.newConstructorForSerialization(c, Object.class.getConstructor());

            return constructor.newInstance();
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }
    }
}
