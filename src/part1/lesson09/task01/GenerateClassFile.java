package part1.lesson09.task01;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * Класс читает с консоли, генерирует класс, компилирует в рантайме
 *
 * @author Алина Мустафина
 * @version 1.0
 */
public class GenerateClassFile {
    /**
     * Путь к классу
     */
    private final String PATH = "src/part1/lesson09/task01/";
    /**
     * Имя класса
     */
    private final String NAME = "SomeClass";
    /**
     * Имя файла
     */
    private final String FILE_NAME = NAME + ".java";
    /**
     * Статус компиляции - удачная или нет
     */
    private int statusCompile;

    public GenerateClassFile() {
        String code = getClassText();
        generateJavaFile(code);
        statusCompile = compileJavaFile();
        System.out.println((statusCompile == 0) ? "Компиляция успешна" : "Ошибки компиляции");
    }

    public int getStatus() {
        return statusCompile;
    }

    /**
     * C консоли построчно считывает код метода doWork
     *
     * @return code - код в виде строки
     */
    private String getClassText() {
        System.out.print(">>");
        StringBuilder sb = new StringBuilder();
        sb.append("package part1.lesson09.task01;\n");
        sb.append("public class " + NAME + " implements Worker {\n");
        sb.append("@Override\n");
        sb.append("public void doWork() {\n");
        boolean b = true;
        Scanner scanner = new Scanner(System.in);
        while (b) {
            String line = scanner.nextLine();
            if (line.length() == 0) { // ввод пустой строки - прекращаем чтение
                b = false;
                continue;
            }
            sb.append(line + ";\n");
        }
        sb.append("}\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Генерирует файл из кода
     *
     * @param code код для метода doWork
     */
    private void generateJavaFile(String code) {
        try (FileOutputStream fos = new FileOutputStream(PATH + FILE_NAME)) {
            fos.write(code.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Компиляция
     *
     * @return статус компиляции
     */
    private int compileJavaFile() {
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();//Возвращает компилятор Java, поставляемый с этой платформой.
        return jc.run(null, null, null, PATH + FILE_NAME);
    }
}
