package part1.lesson03.task02;

import java.util.*;

/*Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox.
Необходимо сделать такую связь, правильно распределить поля и методы.
Функциональность в целом должна сохраниться. При попытке положить Object в MathBox должно создаваться исключение.*/
public class MathBox extends ObjectBox<HashSet<Number>> {

    public MathBox(Number[] arrNums) {
        super(new HashSet<>());
        collection.addAll(Arrays.asList(arrNums));
    }

    @Override
    public void addObject(Object o) {
        try {
            if (o.getClass().getName().equals("java.lang.Object")) {
                throw new ClassCastException("Попытка положить Object в MathBox");
            }
            super.addObject(o);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Сумма всех элементов коллекции
     *
     * @return - агрегированное значение коллекции
     */
    Number summator() {
        double sum = 0;
        Iterator<Number> i = collection.iterator();
        while (i.hasNext()) {
            sum += i.next().doubleValue();
        }
        return sum;
    }

    /**
     * Поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода
     * Хранящиеся в объекте данные полностью заменяются результатами деления
     */
    void splitter(double divider) {
        HashSet<Number> result = new HashSet<>();// чтобы не потерять элемент создаю новый Set
        Iterator<Number> i = collection.iterator();
        try {
            while (i.hasNext()) {
                Number t = i.next();
                Double d = t.doubleValue() / divider;
                if (d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY) {
                    throw new ArithmeticException();
                }
                if (Math.abs(divider) == 1.0 &&
                        (t.getClass().getName().equals("java.lang.Byte") ||
                                t.getClass().getName().equals("java.lang.Short") ||
                                t.getClass().getName().equals("java.lang.Integer") ||
                                t.getClass().getName().equals("java.lang.Long")
                        )
                ) {
                    result.add(d.intValue());
                } else {
                    result.add(d);
                }
            }
            collection = result;
        } catch (ArithmeticException e) {
            System.out.println("Попытка деления на 0. Заменить делитель!");
        }
    }

    @Override
    public String toString() {
        String result = "{";
        int elemCount = 0;
        Iterator<Number> i = collection.iterator();
        while (i.hasNext()) {
            Number nxt = i.next();
            result += ((elemCount++ > 0) ? ", " : "").concat(nxt.toString());
        }
        return result + "}";
    }

    /*? hashcode  в некоторых примерах возвращается кодПоля*31 или *29 - что это означает, какой множитель использовать*/
    @Override
    public int hashCode() {
        return (collection == null) ? 0 : collection.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox mb = (MathBox) o;
        return Objects.equals(collection, mb.collection);
    }

    /**
     * Получает на вход Integer и если такое значение есть в коллекции, удаляет его
     *
     * @param forRemove - искомое и удаляемое значение
     */
    public void delElem(Integer forRemove) {
        Iterator<Number> i = collection.iterator();
        while (i.hasNext()) {
            Number item = i.next();
            if (item.equals(forRemove)) {
                collection.remove(item);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Number[] arrNum = new Number[]{0, 4.56, Math.PI, -1, 10000};
        MathBox mathBox = new MathBox(arrNum);
        mathBox.addObject(new Object());
        System.out.println("Did Object add? result: " + mathBox);
        mathBox.addObject(2.5);
        System.out.println("Did Number add? result: " + mathBox);
        mathBox.delElem(10000);
        System.out.println("after delete return     " + mathBox);
        System.out.println("summator return         " + mathBox.summator());
        mathBox.splitter(2);
        System.out.println("splitter return         " + mathBox);
    }
}
