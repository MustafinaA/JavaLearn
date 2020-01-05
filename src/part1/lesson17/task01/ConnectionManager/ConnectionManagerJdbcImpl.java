package part1.lesson17.task01.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для работы с соединением к БД
 *
 * @author Alina
 * @version 1.0
 */
public class ConnectionManagerJdbcImpl implements ConnectionManager {

    private static ConnectionManager connectionManager;

    ConnectionManagerJdbcImpl() {
    }

    /**
     * метод получения экземпляра
     *
     * @return менеджер соединения
     */
    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerJdbcImpl();
        }
        return connectionManager;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/study",
                    "postgres",
                    "qwerty");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
