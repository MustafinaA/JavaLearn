package part1.lesson05.task01;
import java.util.Random;

public class RandomGenerateItem {
    /**
     * алфавит для имен
     */
    final static String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Генерирует случайное "имя" - набор до 10 букв алфавита
     *
     * @return - имя
     */
    public static String randomName() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = new Random().nextInt(1) + 1;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(new Random().nextInt(lexicon.length())));
            }
        }
        return builder.toString();
    }

    /**
     * Генерирует случайный вес в заданном диапазоне
     * @param min - значение от
     * @param max - значение до
     * @param r - количество знаков (степень округления)
     * @return - вес до r знаков
     */
    public static double randomWeight(double min, double max, double r){
        double roundPower = Math.pow(10.0, r);
        return Math.round(((max-min)*Math.random()+min)*roundPower)/roundPower;
    }
}
