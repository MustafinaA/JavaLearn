package part1.lesson10.task02.client;


import part1.lesson10.task02.MyProperties;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Клиентская логика
 * создание сокета, чтение и запись сообщения. отправка сообщения
 */
public class ClientImpl {
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток чтения в сокет
    private Socket socket;
    private String nickname;

    /**
     * Создает сокет и буферы для чтения и записи
     * стартует новый Thread для слушателя
     *
     * @param name имя клиента
     * @param port порт
     * @param host IP где искать сервер
     */
    public ClientImpl(String name, int port, String host) {
        try {
            this.nickname = name;
            socket = new Socket(host, port);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ClientListener listener = new ClientListener(in);
            new Thread(listener).start();
            sendMessage(nickname);
            Scanner scanner = new Scanner(System.in);
            String text = "";
            while (!MyProperties.QUIT_PHRASE.equals(text)) {
                text = scanner.nextLine();
                sendMessage(text);
            }
            close();
        } catch (UnknownHostException e) {
            System.out.println("Класс сокета не смог преобразовать " + host + " в реальный адрес. IP-адрес хоста не может быть определен");
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

    /**
     * Закрытие всех соединений и буфферов
     */
    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}