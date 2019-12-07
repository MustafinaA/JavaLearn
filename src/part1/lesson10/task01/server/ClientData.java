package part1.lesson10.task01.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Данные клиентов, которые хранятся на сервере, пока сервер запущен
 * @author Alina
 * @version 1.0
 */
public class ClientData {
    private Map<String, Connection> connections;
    private List<String> names;

    ClientData() {
        this.connections = new HashMap<>();
        this.names = new ArrayList<>();
    }

    Map<String, Connection> getConnections() {
        return connections;
    }

    /**
     * Добавление нового клиента
     * @param name - имя
     * @param connection - соединение
     */
    public void add(String name, Connection connection) {
        connections.put(name, connection);
        names.add(name);
    }
}
