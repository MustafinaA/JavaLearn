package part1.lesson06.task01;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.TreeSet;

/**
 * Попытка прочитать файл, если нет файла, то создаст файл, запишет текст, потом прочитает и запишет уже в результируещем файле
 * в отсортированном виде повстречавшиеся слова без дубликатов
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class FileReadWriteDemo {
    private final static String someText = "Задание 1. Написать программу, читающую текстовый файл.\n" +
            "Программа должна составлять отсортированный по алфавиту список слов, найденных в файле и сохранять его в файл-результат.\n" +
            " Найденные слова не должны повторяться, регистр не должен учитываться. Одно слово в разных падежах – это разные слова.";

    /**
     * Строка символов разделителей
     */
    private final static String splitChars = ".,;-–!?";

    private final static String fileIn = "noteIn.txt";
    private final static String fileOut = "noteOut.txt";

    public static void main(String[] args){
        writeFile(fileIn, null);
        addToFile();
        writeFile(fileOut, null);
        TreeSet<String> wordsSet = readFile();
        writeFile(fileOut, wordsSet);
    }

    /**
     * Чтение файла
     * ??? В finally блок вывел предупреждение на return
     */
    private static TreeSet<String> readFile() {
        TreeSet<String> result = new TreeSet<>();
        try (FileInputStream fis = new FileInputStream(FileReadWriteDemo.fileIn);
             Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            int i;
            StringBuilder word = new StringBuilder();
            while ((i = in.read()) != -1) {
                char c = Character.toUpperCase((char) i);
                if (!Character.isWhitespace(c) && splitChars.indexOf(c) == -1) {
                    word.append(c);
                } else if (word.length() > 0) {
                    result.add(word.toString());
                    word = new StringBuilder();
                }
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Вывод в файл
     *
     * @param fileName - имя/путь файла
     * @param content  - что выводим
     */
    private static void writeFile(String fileName, TreeSet<String> content) {
        try (FileOutputStream fos = new FileOutputStream(fileName, true);
             Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8)
        ) {
            if (content != null) {
                for (String w : content) {
                    out.write(w + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToFile() {
        try (FileOutputStream fos = new FileOutputStream(FileReadWriteDemo.fileIn, true);
             Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            out.write(FileReadWriteDemo.someText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
