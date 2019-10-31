package part1.lesson02.task01;
/*
Написать программу ”Hello, World!”. В ходе выполнения программы она должна выбросить исключение и завершиться с ошибкой.
Смоделировав ошибку «NullPointerException»
Смоделировав ошибку «ArrayIndexOutOfBoundsException»
Вызвав свой вариант ошибки через оператор throw
*/

/**
 * Класс демонстрации выброса ошибок NullPointerException, ArrayIndexOutOfBoundsException и {@link MyException}.
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class ExceptionDemo {
    /**
     * Массив строк, участвующий в моделировании случаев возникновения ошибок
     */
    private static String[] strArr = {null, "null"};

    public static void main(String[] args) throws MyException {
        try {
            throw new NullPointerException();
        } catch (NullPointerException e) {
            System.out.println("Исключение "+e.getClass().getName()+" передано явно и перехвачено.");
        }
        for (int i = 0; i < strArr.length; i++) {
            try {
                System.out.println(strArr[i].equals(strArr[i + 1]));
            } catch (NullPointerException e) {
                System.out.println("Исключение NullPointerException смоделировано и перехвачено.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Исключение ArrayIndexOutOfBoundsException смоделировано и перехвачено.");
            } finally {
                if (i == strArr.length - 1) {
                    throw new MyException("Свой вариант ошибки.");
                }
            }
        }
    }

    /**
     * Частный тип исключений
     */
    static class MyException extends Exception {
        /**
         * Конструктор
         * @param outString - строка для вывода
         */
        MyException(String outString) {
            System.out.println(outString);
        }
    }
}
