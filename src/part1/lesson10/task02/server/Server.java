package part1.lesson10.task02.server;
/*Задание 2.  Усовершенствовать задание 1:
a.      добавить возможность отправки личных сообщений (unicast).
b.      добавить возможность выхода из чата с помощью написанной в чате команды «quit»
*/

import part1.lesson10.task02.MyProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Класс сервера
 *
 * @author Alina
 * @version 1.0
 */
public class Server {

    private static ClientData serverList = new ClientData(); // коллекция всех нитей - экземпляров сервера, слушающих каждый своего клиента

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(MyProperties.SERVER_PORT, 0, InetAddress.getByName(MyProperties.ADDRESS))) {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();// accept() будет ждать пока кто-нибудь не захочет подключиться
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String name = reader.readLine();
                Connection connection = new Connection(socket, name, serverList, reader); //есть новое подключение, инициируем его
                serverList.add(name, connection); // добавим новое соединение в коллекцию
                connection.broadcast(name + " присоединился к чату");
                new Thread(connection).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}