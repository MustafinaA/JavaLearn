package part1.lesson06.task01;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;

/**
 * Попытка прочитать файл, если нет файла, то создаст файл, запишет текст, потом прочитает и запишет уже в результируещем файле
 * в отсортированном виде повстречавшиеся слова без дубликатов
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class FileReadWriteDemo {
    private final static String someText = "Задание 1. Написать программу, 'читающую' текстовый файл.\n" +
            "Программа должна составлять отсортированный по алфавиту: список слов, найденных в файле и сохранять его в файл-результат.\n" +
            " Найденные слова не должны повторяться, регистр не должен учитываться. Одно слово в разных падежах – это разные слова.";
    private final static String fileIn = "noteIn.txt";
    private final static String fileOut = "noteOut.txt";

    public static void main(String[] args){
        writeFile(fileIn, someText);
        Set<String> wordsSet = readFile();
        writeFile(fileOut, wordsSet);
    }

    /**
     * Чтение файла
     */
    private static Set<String> readFile() {
        Set<String> result = new TreeSet<>();
        try (FileInputStream fis = new FileInputStream(FileReadWriteDemo.fileIn);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (String word : line.split("[\\p{P}\\s-]+")){
                    if(word.length()>0 && !result.contains(word.toLowerCase())) {
                        result.add(word.toLowerCase());
                    }
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Файл не найден");
        }catch (IOException e){
            System.out.println("Файл нельзя прочитать");
        }
        return result;
    }

    /**
     * Вывод в файл
     *
     * @param fileName - имя/путь файла
     * @param content  - что выводим
     */
    private static void writeFile(String fileName, Set<String> content) {
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

    /**
     * Запись текста в файл
     * @param fileName - имя файла
     * @param content - выводимое строковое содержимое
     */
    private static void writeFile(String fileName, String content) {
        try (FileOutputStream fos = new FileOutputStream(fileName, true);
             Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}