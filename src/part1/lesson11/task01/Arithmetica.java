package part1.lesson11.task01;

/*
Задание: Перевести одну из предыдущих работ на использование стримов и лямбда-выражений там, где это уместно (возможно, жертвуя производительностью)
Составить программу, генерирующую N случайных чисел. Для каждого числа k вычислить квадратный корень q.
Если квадрат целой части q числа равен k, то вывести это число на экран.
Предусмотреть что первоначальные числа могут быть отрицательные, в этом случае генерировать исключение.
 */

import javafx.util.Pair;
import java.util.Arrays;
import java.util.Random;

/**
 * Класс демонстрации арифметических вычислений c использованием лямбда-выражений
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class Arithmetica {
    /**
     * Количество чисел
     */
    private static long N = 10000;

    public static void main(String[] args) {
        Arrays.stream(new Random()
                .longs(N, -10, 1000)
                .toArray())
                .mapToObj(Arithmetica::calcSqrt)
                .filter(q -> !Double.isNaN(q.getValue()) && (q.getValue().longValue() * q.getValue().longValue() == q.getKey()))
        .forEach(System.out::println);    }

    private static Pair<Long,Double> calcSqrt(long k) {
        try {
            if (k > 0) {
                return new Pair<>(k,Math.sqrt(k));
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println(k + " Квадратный корень от отрицательного числа равен NaN");
        }
        return new Pair<>(k,Double.NaN);
    }
}
