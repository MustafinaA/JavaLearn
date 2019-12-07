package part1.lesson10.task01.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Клиентская логика
 * создание сокета, чтение и запись сообщения. отправка сообщения
 */
public class ClientImpl {
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток чтения в сокет
    private BufferedReader inputUser; // поток чтения с консоли
    private String nickname;// ник клиента
    private Date time;
    private String dtime;
    private SimpleDateFormat dt1;

    public ClientImpl(String name, int port, String host) {
        try {
            this.nickname = name;
            Socket socket = new Socket(host, port);// новый запрос на соединение
            // потоки чтения из сокета / записи в сокет, и чтения с консоли
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            sendMessage(name);//отсылка эхо с именем на сервер
            new ReadMsg().start(); // нить читающая сообщения из сокета в бесконечном цикле
            new WriteMsg().start(); // нить пишущая сообщения в сокет приходящие с консоли в бесконечном цикле
        } catch (UnknownHostException e) {
            System.out.println("Класс сокета не смог преобразовать "+host+" в реальный адрес. IP-адрес хоста не может быть определен");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Потеря соединения");
            e.printStackTrace();
        }
    }

    /**
     * Отправка сообщения серверу
     *
     * @param message - текст сообщения
     */
    public void sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush(); // выталкивает содержимое буфера
        } catch (IOException e) {
            System.out.println("Проблемы с вводом/выводом на клиенте");
            e.printStackTrace();
        }
    }

    // Thread чтения сообщений с сервера
    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine(); // ждем сообщения с сервера
                    System.out.println(str); // пишем сообщение с сервера на консоль
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // нить отправляющая сообщения приходящие с консоли на сервер
    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                String message;
                try {
                    time = new Date(); // текущая дата
                    dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
                    dtime = dt1.format(time); // время
                    message = inputUser.readLine(); // сообщения с консоли
                    out.write("(" + dtime + ") " + nickname + ": " + message + "\n"); // отправляем на сервер
                    out.flush(); // чистим
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
