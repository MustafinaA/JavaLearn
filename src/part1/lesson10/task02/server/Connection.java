package part1.lesson10.task02.server;

import part1.lesson10.task02.MyProperties;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Connection implements Runnable {

    Socket socket;
    BufferedReader reader;
    String name;
    ClientData clientData;
    BufferedWriter writer;
    private Date time;
    private String dtime;
    private SimpleDateFormat dt1;

    public Connection(Socket socket, String name, ClientData clientData, BufferedReader reader) {
        this.clientData = clientData;
        this.name = name;
        this.socket = socket;
        this.reader = reader;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запуск потока со слушателем для клиента и отправка сообщения всем клиентам в чате
     */
    @Override
    public void run() {
        try {
            String message;
            time = new Date(); // текущая дата
            dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
            dtime = dt1.format(time); // время
            while (true) {
                message = reader.readLine();
                if (MyProperties.QUIT_PHRASE.equals(message)) {
                    clientData.remove(name);
                    broadcast(name + " вышел из чата");
                    break;
                }
                if (message.charAt(0) == '@') {
                    String nickname = message.substring(1, message.indexOf(' '));
                    unicast(nickname, "(" + dtime + ") " + name + ": " + message);
                    continue;
                }
                broadcast("(" + dtime + ") " + name + ": " + message);
            }
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            clientData.remove(name);
            broadcast(name + " вышел из чата");
        }
    }

    /**
     * Отправка личных сообщений (unicast).
     *
     * @param nickname клиент, которому отправляют сообщение
     * @param message  - сообщение
     */
    private void unicast(String nickname, String message) {
        send(message);
        Connection connection = clientData.getConnections().get(nickname);
        connection.send(message);
    }

    /**
     * Отправка сообщения от сервера клиенту
     *
     * @param message сообщение
     */
    private void send(String message) {
        try {
            System.out.println("write");
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправить сообщение всем клиентам в чате
     *
     * @param message - сообщение
     */
    public void broadcast(String message) {
        List<String> clients = clientData.getNames();
        Map<String, Connection> connectionMap = clientData.getConnections();
        for (String name : clients) {
            Connection connection = connectionMap.get(name);
            connection.send(message);
        }
    }
}