package part1.lesson06.task02;
/*
Задание 2. Создать генератор текстовых файлов, работающий по следующим правилам:

Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
Слово состоит из 1<=n2<=15 латинских букв
Слова разделены одним пробелом
Предложение начинается с заглавной буквы
Предложение заканчивается (.|!|?)+" "
Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива в следующее предложение (1/probability).
Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability),
который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * @author Алина Мустафина
 * @version 1.0
 */
public class TextFilesGenerator {
    private static Random random = new Random();
    /**
     * алфавит
     */
    private final static String lexicon = "abcdefghijklmnopqrdtuvwxyz";

    /**
     * Символы, на которые по заданному правилу может завершиться предложение
     */
    private final static char[] sentenceFinalChars = {'.', '!', '?'};

    public static void main(String[] args) {
        String path = "files";
        int n = 20;
        int size = getIntRandom(0, 30000);//10КБ
        String[] words = getStringArrayRandom(getIntRandom(1, 1000));
        int probability = getIntRandom(0, 100);

        Path dirPath = Paths.get(path);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getFiles(path, n, size, words, probability);
    }

    /**
     * Основной метод создания n файлов размером size в каталоге path. words - массив слов, probability - вероятность
     *
     * @param path        - директория
     * @param n           - количество файлов
     * @param size        - максимальный размер файла
     * @param words       - массив предложенных слов
     * @param probability - вероятность
     */
    private static void getFiles(String path, int n, int size, String[] words, int probability) {
        for (int i = 0; i < n; i++) {
            StringBuilder builder = new StringBuilder();
            int tempSize = 0;
            do {
                StringBuilder abzac = getRandomAbzac(words, probability);
                if (tempSize + abzac.toString().getBytes(StandardCharsets.UTF_8).length > size) {
                    break;
                }
                builder.append(abzac);
                tempSize = builder.toString().getBytes(StandardCharsets.UTF_8).length;
            } while (tempSize < size);
            try (FileOutputStream fos = new FileOutputStream(path + "/gen-text-file-" + i + ".txt");
                 Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8)
            ) {
                out.write(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Получить рандомный массив из n слов
     *
     * @param size - размерность массива
     * @return - массив слов - наш словарь
     */
    private static String[] getStringArrayRandom(int size) {
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder sb = getRandomWord(result, 0);
            result[i] = sb.toString();
        }
        return result;
    }

    /**
     * Генерирует случайное слово - набор до 15 букв алфавита или возвращает слово из словаря
     *
     * @param words       - словарь слов
     * @param probability - вероятность вхождения одного из слов словаря в предложение
     * @return - слово
     */
    private static StringBuilder getRandomWord(String[] words, int probability) {
        StringBuilder result = new StringBuilder();
        if (getIntRandom(0, 100) < probability) {
            result.append(words[getIntRandom(0, words.length)]);
            return result;
        }
        while (result.length() == 0) {
            int length = getIntRandom(1, 15);
            for (int i = 0; i < length; i++) {
                result.append(lexicon.charAt(getIntRandom(0, lexicon.length())));
            }
        }
        return result;
    }

    /**
     * Получение абзаца
     *
     * @param words       - словарь
     * @param probability - вероятность вхождения одного из слов словаря в предложение
     * @return - абзац текста
     */
    private static StringBuilder getRandomAbzac(String[] words, int probability) {
        StringBuilder result = new StringBuilder();
        int sentenceCount = getIntRandom(1, 20);
        for (int i = 0; i < sentenceCount; i++) {
            StringBuilder sentence = getRandomSentence(words, probability);
            result.append(sentence);
        }
        result.append("\n");
        return result;
    }

    /**
     * Получение предложения
     *
     * @param words       - словарь
     * @param probability - вероятность вхождения одного из слов словаря в предложение
     * @return - предложение
     */
    private static StringBuilder getRandomSentence(String[] words, int probability) {
        StringBuilder result = new StringBuilder();
        int wordCount = getIntRandom(1, 15);
        for (int i = 0; i < wordCount; i++) {
            StringBuilder word = getRandomWord(words, probability);
            if (i == 0) {
                String firstWordInSentence = word.toString();
                firstWordInSentence = firstWordInSentence.substring(0, 1).toUpperCase() + firstWordInSentence.substring(1);
                result.append(firstWordInSentence);
            } else {
                result.append(word);
            }
            if (i < wordCount - 1) {
                result.append(random.nextDouble() > 0.7 ? ", " : " ");
            }
        }
        result.append(sentenceFinalChars[getIntRandom(0, (sentenceFinalChars.length) - 1)]).append(" ");
        return result;
    }

    /**
     * Получение случайного целого в диапазоне
     *
     * @param min - минимальное значение
     * @param max - максимальное значение
     * @return - случайное целое число
     */
    private static int getIntRandom(int min, int max) {
        return min + random.nextInt(max - min);
    }

}
