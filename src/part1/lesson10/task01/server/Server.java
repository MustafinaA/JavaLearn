package part1.lesson10.task01.server;
/*Задание 1. Разработать приложение - многопользовательский чат, в котором участвует произвольное количество клиентов.
Каждый клиент после запуска отправляет свое имя серверу.
После чего начинает отправлять ему сообщения.
Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
*/
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static part1.lesson10.task01.MyProperties.SERVER_PORT;

/**
 * Класс сервера
 * @author Alina
 * @version 1.0
 */
public class Server {
    static ClientData serverList = new ClientData(); // коллекция всех нитей - экземпляров сервера, слушающих каждый своего клиента
    private static ServerSocket server; // серверсокет

    public static void main(String[] args) throws IOException {
        server = new ServerSocket(SERVER_PORT); // серверсокет прослушивает порт SERVER_PORT
        System.out.println("Сервер запущен!");
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();// accept() будет ждать пока кто-нибудь не захочет подключиться
                try {
                    Connection connection = new Connection(socket); //есть новое подключение, инициируем его
                    serverList.add(connection.getName(), connection); // добавим новое соединение в коллекцию
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}