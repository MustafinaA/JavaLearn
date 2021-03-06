package part1.lesson10.task02.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Данные клиентов, которые хранятся на сервере, пока сервер запущен
 *
 * @author Alina
 * @version 1.0
 */
public class ClientData {
    private Map<String, Connection> connections;
    private List<String> names;

    public ClientData() {
        this.connections = new HashMap<>();
        this.names = new ArrayList<>();
    }

    public Map<String, Connection> getConnections() {
        return connections;
    }

    public List<String> getNames() {
        return names;
    }

    /**
     * Добавление нового клиента
     *
     * @param name       - имя
     * @param connection - соединение
     */
    public void add(String name, Connection connection) {
        connections.put(name, connection);
        names.add(name);
    }

    /**
     * Удаление клиента из хранилища, когда он выходит из чата
     *
     * @param name имя клиента
     */
    public void remove(String name) {
        connections.remove(name);
        names.remove(name);
    }
}
