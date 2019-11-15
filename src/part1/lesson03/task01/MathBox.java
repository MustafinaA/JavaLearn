package part1.lesson03.task01;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Objects;
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
class MathBox {
    /**
     * Массив Number или подклассов
     */
    private Set<Number> nums;

    MathBox(Number[] arrNums) {
        nums = convertArrayToSet(arrNums);
    }

    /**
     * Элементы массива внутри объекта раскладываются в Set
     *
     * @param array - массив
     * @return - коллекция чисел
     */
    Set<Number> convertArrayToSet(Number array[]) {
        Set<Number> set = new HashSet<>();
        for (Number t : array) {
            set.add(t);
        }
        return set;
    }

    /**
     * Сумма всех элементов коллекции
     *
     * @return - агрегированное значение коллекции
     */
    Number summator() {
        double sum = 0;
        for (Number n : nums) {
            sum += n.doubleValue();
        }
        return sum;
    }

    /**
     * Поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода
     * Хранящиеся в объекте данные полностью заменяются результатами деления
     */
    void splitter(double divider) {
        Set<Number> result = new HashSet<>();// чтобы не потерять элемент создаю новый Set
        try {
            for (Number num : nums) {
                double d = num.doubleValue() / divider;
                if (d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY) {
                    throw new ArithmeticException();
                }
                if (Math.abs(divider) == 1.0 &&
                        (num.getClass().getName().equals("java.lang.Byte") ||
                                num.getClass().getName().equals("java.lang.Short") ||
                                num.getClass().getName().equals("java.lang.Integer") ||
                                num.getClass().getName().equals("java.lang.Long")
                        )
                ) {
                    result.add((int) d);
                } else {
                    result.add(d);
                }
            }
            nums = result;
        } catch (ArithmeticException e) {
            System.out.println("Попытка деления на 0. Заменить делитель!");
        }
    }

    @Override
    public String toString() {
        String result = "{";
        int elemCount = 0;
        for (Number num : nums) {
            result += ((elemCount++ > 0) ? ", " : "").concat(num.toString());
        }
        return result + "}";
    }

    @Override
    public int hashCode() {
        return (nums == null) ? 0 : 31 * nums.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox mb = (MathBox) o;
        return Objects.equals(nums, mb.nums);
    }

    /**
     * Получает на вход Integer и если такое значение есть в коллекции, удаляет его
     *
     * @param forRemove - искомое и удаляемое значение
     */
    public void delElem(Integer forRemove) {
        for (Number num : nums) {
            if (num.equals(forRemove)) {
                nums.remove(num);
                break;
            }
        }
    }
}