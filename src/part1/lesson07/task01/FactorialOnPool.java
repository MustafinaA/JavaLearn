package part1.lesson07.task01;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

import static java.lang.String.format;

/*
Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива.
Использовать пул потоков для решения задачи.
Особенности выполнения:
Для данного примера использовать рекурсию - не очень хороший вариант, т.к. происходит большое выделение памяти, очень вероятен StackOverFlow.
Лучше перемножать числа в простом цикле при этом создавать объект типа BigInteger
По сути, есть несколько способа решения задания:
1) распараллеливать вычисление факториала для одного числа
2) распараллеливать вычисления для разных чисел
3) комбинированный
При чем вычислив факториал для одного числа, можно запомнить эти данные и использовать их для вычисления другого, что будет гораздо быстрее
 */

/**
 * Вычисление факторила случайных чисел в пуле потоков
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class FactorialOnPool {
    /**
     * Количество чисел для вычисления факториала
     */
    private static final int NUMBER_COUNT = 1000;
    /**
     * Максимум для генерируемых чисел
     */
    private static final int NUMBER_MAX = 1000;
    /**
     * Список случайных чисел для вычисления факториалов
     */
    private static List<Integer> arrRandomNums = new ArrayList<>();
    /**
     * Хранилище вычисленных факториалов
     * NavigableMap - т.к. надо определять вычисленный факториал для числа меньшего чем текущее
     */
    private static NavigableMap<Integer, BigInteger> computedFactorials = new TreeMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < NUMBER_COUNT; i++) {
            arrRandomNums.add(new Random().nextInt(NUMBER_MAX));
        }
        long start = System.nanoTime();
        List<FutureTask<BigInteger>> getFactorialTasks = new ArrayList<>();
        int taskInd = 0;// порядковый номер task
        for (Integer tempNum : arrRandomNums) {
            BigInteger factorial = computedFactorials.get(tempNum);
            if (factorial == null) {
                computedFactorials.put(tempNum, null);
                Callable<BigInteger> callable;
                if (computedFactorials.firstKey().equals(tempNum)) {
                    callable = new Factorial(tempNum);
                } else {
                    Map.Entry<Integer, BigInteger> previItemOnTree = computedFactorials.lowerEntry(tempNum);
                    callable = new Factorial(tempNum, previItemOnTree.getKey(), previItemOnTree.getValue());
                }
                getFactorialTasks.add(new FutureTask(callable));
                threadPool.execute(getFactorialTasks.get(taskInd));
                computedFactorials.put(tempNum, getFactorialTasks.get(taskInd++).get());
            }
        }
        threadPool.shutdown();
        arrRandomNums.forEach(x->System.out.println(x+"->"+ computedFactorials.get(x)));
        System.out.println(System.nanoTime() - start);
    }
}
