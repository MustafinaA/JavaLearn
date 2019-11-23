package part1.lesson07.task01;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * Вычисление факториала числа
 * @author Алина Мустафина
 * @version 1.0
 */
public class Factorial implements Callable<BigInteger> {
    /**
     * Число для вычисления факториала
     */
    private int num;
    /**
     * число-индекс для начала вычисления факториала
     */
    private int startI = 0;
    /**
     * Значение вычесленного факторила для startI
     */
    private BigInteger startBI = new BigInteger("1");

    Factorial(int num) {
        this.num = num;
    }

    Factorial(int num, int startI, BigInteger startBI) {
        this.num = num;
        this.startI = startI;
        this.startBI = startBI;
    }

    /**
     * Вычисление факториала
     * @param num для какого числа вычисляется факториал
     * @param startI число, для которого уже известен факториал
     * @param startBI известный факториал для числа startI
     * @return значение
     */
    private BigInteger calc(int num, int startI, BigInteger startBI) {
        BigInteger result = startBI;//new BigInteger("1");
        for (int i = startI+1; i <= num; i++) {
            result = result.multiply(new BigInteger("" + i));// умножение двух BigInteger
        }
        return result;
    }

    @Override
    public BigInteger call() {
        return calc(num, startI, startBI);
    }
}
