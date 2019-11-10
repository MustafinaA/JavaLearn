package part1.lesson03.task01_;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*Написать класс MathBox, реализующий следующий функционал:

+Конструктор на вход получает массив Number. Элементы не могут повторяться.
+Элементы массива внутри объекта раскладываются в подходящую коллекцию (выбрать самостоятельно).
+Существует метод summator, возвращающий сумму всех элементов коллекции.
+Существует метод splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода.
+Хранящиеся в объекте данные полностью заменяются результатами деления.
Необходимо правильно переопределить методы toString, hashCode, equals,
чтобы можно было использовать MathBox для вывода данных на экран и хранение объектов этого класса в коллекциях (например, hashMap).
Выполнение контракта обязательно!
Создать метод, который получает на вход Integer и если такое значение есть в коллекции, удаляет его.*/
//class MathBox {
//    /**
//     * Массив Number или подклассов
//     */
//    private Set<Number> nums;
//
//    MathBox(Number[] arrNums) {
//        nums = convertArrayToSet(arrNums);
//    }
//
//    /**
//     * Элементы массива внутри объекта раскладываются в Set
//     *
//     * @param array - массив
//     * @return - коллекция чисел
//     */
//    Set<Number> convertArrayToSet(Number array[]) {
//        Set<Number> set = new HashSet<>();
//        for (Number t : array) {
//            set.add(t);
//        }
//        return set;
//    }
//    /**
//     * Сумма всех элементов коллекции
//     * @return - агрегированное значение коллекции
//     */
//    Number summator() {
//        double sum = 0;
//        Iterator<Number> i = nums.iterator();
//        while (i.hasNext()) {
//            sum += i.next().doubleValue();
//        }
//        return sum;
//    }
//
//    /**
//     * Поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода
//     * Хранящиеся в объекте данные полностью заменяются результатами деления
//     */
//    void splitter(double divider) {
//        Set<Number> result = new HashSet<>();// чтобы не потерять элемент создаю новый Set
//        Iterator<Number> i = nums.iterator();
//        try {
//            while (i.hasNext()) {
//                Number t = i.next();
//                Double d = t.doubleValue() / divider;
//                if (d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY) {
//                    throw new ArithmeticException();
//                }
//                if (Math.abs(divider) == 1.0 &&
//                        (t.getClass().getName().equals("java.lang.Byte") ||
//                                t.getClass().getName().equals("java.lang.Short") ||
//                                t.getClass().getName().equals("java.lang.Integer") ||
//                                t.getClass().getName().equals("java.lang.Long")
//                        )
//                ) {
//                    result.add(d.intValue());
//                } else {
//                    result.add(d);
//                }
//            }
//            nums = result;
//        } catch (ArithmeticException e) {
//            System.out.println("Попытка деления на 0. Заменить делитель!");
//        }
//    }
//
//    @Override
//    public String toString() {
//        String result = "{";
//        int elemCount = 0;
//        Iterator<Number> i = nums.iterator();
//        while (i.hasNext()){
//            Number nxt = i.next();
//            result += ((elemCount++>0)?", ":"").concat(nxt.toString());
//        }
//        return result+"}";
//    }
//
//    /*? hashcode  в некоторых примерах возвращается кодПоля*31 или *29 - что это означает, какой множитель использовать*/
//    @Override
//    public int hashCode() {
//        return (nums == null) ? 0 : nums.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        MathBox mb = (MathBox) o;
//        return Objects.equals(nums, mb.nums);
//    }
//
//    /**
//     * Получает на вход Integer и если такое значение есть в коллекции, удаляет его
//     * @param forRemove - искомое и удаляемое значение
//     */
//    public void delElem(Integer forRemove){
//        Iterator<Number> i = nums.iterator();
//        while (i.hasNext()){
//            Number item = i.next();
//            if(item.equals(forRemove)){
//                nums.remove(item);
//                break;
//            }
//        }
//    }
//}
class MathBox<T extends Number>{
    /**
     * Массив Number или подклассов
     */
    private Set<T> nums;

    public MathBox(T[] arrNumber) {
        nums = convertArrayToSet(arrNumber);
    }

    /**
     * Элементы массива внутри объекта раскладываются в Set
     * @param array - массив
     * @param <T> - тип элементов массива
     * @return - коллекция чисел
     */
    <T> Set<T> convertArrayToSet(T array[]) {
        Set<T> set = new HashSet<>();
        for (T t : array) {
            set.add(t);
        }
        return set;
    }

    /**
     * Сумма всех элементов коллекции
     * @return - агрегированное значение коллекции
     */
    T summator(Arithmetic<T> arithmetic){
        T sum = arithmetic.zero();
        Iterator<T> i = nums.iterator();
        while (i.hasNext()){
            sum = arithmetic.add(sum, i.next());
        }
        return sum;
    }

    /**
     * Поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода
     * ??? Хранящиеся в объекте данные полностью заменяются результатами деления, поэтому я сделала вывод, что делителем может иметь только целочисленный тип
     */
    void splitter(int divider){
        Set<Number> result = new HashSet<>();// чтобы не потерять элемент создаю новый Set
        Iterator<T> i = nums.iterator();
        try{
            while (i.hasNext()){
                T t = i.next();
                switch (t.getClass().getName()){
                    case "java.lang.Byte":
                    case "java.lang.Short":
                    case "java.lang.Integer":
                    case "java.lang.Long":
                        result.add(t.longValue()/divider);
                        break;
                    case "java.lang.Number":
                    case "java.lang.Double":
                    case "java.lang.Float":
                        result.add(t.floatValue()/divider);
                }
            }
            nums = (Set<T>) result;
        }catch(ArithmeticException e){
            System.out.println("Попытка деления на 0. Заменить делитель!");
        }
    }

    @Override
    public String toString() {
        String result = "{";
        int elemCount = 0;
        Iterator<T> i = nums.iterator();
        while (i.hasNext()){
            T nxt = i.next();
            result += ((elemCount++>0)?", ":"").concat(nxt.toString());
        }
        return result+"}";
    }
}

/**
 * Обобщенный интерфейс для скложения
 * @param <T> - тип от Number
 */
interface Arithmetic<T extends Number> {
    T zero(); //Добавление нуля
    T add(T a, T b); //сложение двух объектов
}