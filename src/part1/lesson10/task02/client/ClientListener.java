package part1.lesson10.task02.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.locks.LockSupport;

/**
 * Класс слушателя клиента
 *
 * @author Alina
 * @version 1.0
 */
public class ClientListener implements Runnable {

    private BufferedReader in;

    public ClientListener(BufferedReader in) {
        this.in = in;
    }

    /**
     * Прослушивание ответа из сообщения в новом потоке
     */
    @Override
    public void run() {
        try {
            String message;
            while (true) {
                message = in.readLine();
                System.out.println(message);
                LockSupport.parkNanos(1000);
            }
        } catch (IOException ignore_exception) {
        }
    }
}