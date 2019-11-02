package part1.lesson02.task02;

/*
Составить программу, генерирующую N случайных чисел. Для каждого числа k вычислить квадратный корень q.
Если квадрат целой части q числа равен k, то вывести это число на экран.
Предусмотреть что первоначальные числа могут быть отрицательные, в этом случае генерировать исключение.
 */

/**
 * Класс демонстрации арифметических вычислений
 *  *
 *  * @author Алина Мустафина
 *  * @version 1.0
 */
public class Arithmetica {
    /**
     * Количество чисел
     */
    private static long N = 10000;

    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            long k = (long) (100.0 * (Math.random() * 2 - 1));
            try {
                if (k > 0) {
                    double q = Math.sqrt(k);
                    if ((long) q * (long) q == k) {
                        System.out.println(k);
                    }
                    continue;
                }
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Квадратный корень от отрицательного числа равен NaN");
            }
        }
    }
}
