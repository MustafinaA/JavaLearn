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
import java.lang.reflect.*;
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
    private final static String SEPARATE = System.getProperty("line.separator");

    public static void main(String[] args) throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        Person person = new Person(list, "Иван", new Company("Alpha", "Russia, Kazan, Mira st. 51"));
        System.out.println("Object before serialization  => " + person.toString());
        serialize(person, "complexObj.txt");
        System.out.println("Object after deserialization  => " + deSerialize("complexObj.txt").toString());
    }

    private static final List LEAVES = Arrays.asList(
            Boolean.class, Character.class, Byte.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class, Void.class,
            String.class);

    /**
     * Процедура рекурсивной записи информации о полях и значениях, установленных для текущего объекта
     *
     * @param object - объект для парса
     * @param stage  - уровень вложенности
     * @throws IllegalAccessException
     */
    public static String toStringRecursive(Object object, int stage) throws IllegalAccessException {
        String ESC_CHR = "%" + stage;
        if (object == null)
            return "null";

        if (LEAVES.contains(object.getClass()))
            return object.toString();

        StringBuilder sb = new StringBuilder();
        sb.append(ESC_CHR).append(object.getClass().getName()).append(ESC_CHR).append(SEPARATE);
        for (Field f : object.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {//статичную кишку пропускаем
                continue;
            }
            f.setAccessible(true);
            if (f.getType().isArray()) {
                Object[] obj = (Object[]) f.get(object);
                try {
                    for (int j = 0; j < obj.length; j++) {
                        sb.append(ESC_CHR).append(obj[j].getClass().getName()).append(ESC_CHR).append(SEPARATE);// тип
                        sb.append(ESC_CHR).append(toStringRecursive(obj[j], stage + 1)).append(ESC_CHR).append(SEPARATE);// значение
                    }
                } catch (NullPointerException npe) {
                    break;
                }

            } else {
                sb.append(ESC_CHR).append(f.getName()).append(ESC_CHR).append(SEPARATE);// имя
                sb.append(ESC_CHR).append(f.getType().getName()).append(ESC_CHR).append(SEPARATE);// тип
                sb.append(ESC_CHR).append(toStringRecursive(f.get(object), stage + 1)).append(ESC_CHR).append(SEPARATE);// значение
            }
        }
        return sb.toString();
    }

    /**
     * Сериализация объекта Object в файл file
     *
     * @param object - сериализуемый объект
     * @param file   - имя файла, за записи сеарилизуемого объекта
     */
    private static void serialize(Object object, String file) {
        File f = new File(file);
        try (FileWriter writer = new FileWriter(f, StandardCharsets.UTF_8)) {
            writer.write(toStringRecursive(object, 0));
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Ошибка сериализации");
            e.printStackTrace();
        }
    }

    private static Object deSerialize(String file) {
        Class<?> clazz = null;
        Object obj = null;
        Field tempField = null;
        Class<?> tempType = null;
        String typeName = null;
        Scanner input;
        try {
            input = new Scanner(new File(file));
            readFieldInfo(clazz, obj, tempField, tempType, input, 0);
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
            System.out.println("Не удалось создать экземпляр класса : " + clazz);
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("Не найдено поле");
            e.printStackTrace();
        }
        return obj;
    }

    private static void readFieldInfo(Class<?> clazz, Object obj, Field tempField, Class<?> tempType, Scanner input, int stage) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Object obj_ = obj;
        String ESC_CHR = "%" + stage;
        while (input.hasNext()) {
            if (clazz == null) {// следующая строка в файле информация о классе
                String inputNext = input.next();
                int formIndex = inputNext.indexOf(ESC_CHR) + ESC_CHR.length();
                int toIndex = inputNext.indexOf(ESC_CHR, ESC_CHR.length() - 1);
                String strValue = inputNext.substring(formIndex, toIndex);
                clazz = Class.forName(strValue);
                obj_ = instance(clazz);//InstantiationException  на obj_ = clazz.newInstance();
            } else {// все остальное относится к филдам
                if (tempField == null) {// имя
                    String inputNext = input.next();
                    int formIndex = inputNext.indexOf(ESC_CHR) + ESC_CHR.length();
                    int toIndex = inputNext.indexOf(ESC_CHR, ESC_CHR.length() - 1);
                    String strValue = inputNext.substring(formIndex, toIndex);
                    tempField = obj_.getClass().getDeclaredField(strValue);
                    tempField.setAccessible(true);
                } else if (tempType == null) {// тип
                    String inputNext = input.next();
                    int formIndex = inputNext.indexOf(ESC_CHR) + ESC_CHR.length();
                    int toIndex = inputNext.indexOf(ESC_CHR, ESC_CHR.length() - 1);
                    String strValue = inputNext.substring(formIndex, toIndex);
                    String typeName = strValue;
                    if (Class.forName(typeName).isPrimitive()) {
                        tempType = TypeUtilities.getPrimitiveTypeByName(typeName);
                    } else {
                        tempType = Class.forName(typeName);
                    }
                } else {// значение
                    System.out.println(tempField.getType());
                    ParameterizedType stringListType = (ParameterizedType) tempField.getGenericType();
                    Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
                    System.out.println(stringListClass.getName()); // class java.lang.String
                    if (tempType instanceof Class && ((Class) tempType).isArray()) {
                        Class componentType = ((Class) tempType).getComponentType();
                        System.out.println("Array component type=" + componentType.getTypeName());
                        // printArrayComponentType(componentType, level + 1);
                    }
                    Type type = tempField.getGenericType();
                    if (type.getClass() != Class.class) {
                        System.out.println("Type class implementing interfaces=" + Arrays.toString(type.getClass().getInterfaces()));
                        System.out.println("Type name= " + type.getTypeName());
//                        getParameterizedType(type, level);
                    }
                    if (LEAVES.contains(tempField.getType())) {
                        String inputNext = input.next();
                        int formIndex = inputNext.indexOf(ESC_CHR) + ESC_CHR.length();
                        int toIndex = inputNext.indexOf(ESC_CHR, ESC_CHR.length() - 1);
                        String strValue = inputNext.substring(formIndex, toIndex);
                        tempField.set(obj, strValue);
                    }
                    if (tempField.getType().isPrimitive()) {
                        String inputNext = input.next();
                        int formIndex = inputNext.indexOf(ESC_CHR) + ESC_CHR.length();
                        int toIndex = inputNext.indexOf(ESC_CHR, ESC_CHR.length() - 1);
                        String strValue = inputNext.substring(formIndex, toIndex);
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
                    } else {
//                        Object attachedObj = tempType.newInstance();
//                        Field attachedField = null;
//                        Class<?> attachedType = null;
//                        readFieldInfo(clazz, attachedObj, attachedField, attachedType, input, stage+1);
//                        //tempField.set(obj, input.next());
                    }
                    tempField = null;
                    tempType = null;
                }
            }
        }
    }

    /**
     * Универсальный вызов конструктора
     *
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
