package part1.lesson10.task01.server;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class Connection extends Thread {
    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        // если потоку ввода/вывода приведут к генерированию исключения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start(); // вызываем run()
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = in.readLine();
                for (Map.Entry<String, Connection> connection : Server.serverList.getConnections().entrySet()) {
                    if(!connection.getValue().equals(this)){
                        connection.getValue().send(message); // отослать принятое сообщение с привязанного клиента всем остальным
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправка сообщения
     * @param message сообщение
     */
    private void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
