package part1.lesson17.test.mocks;

import part1.lesson17.task01.ConnectionManager.ConnectionManager;

import java.sql.Connection;
/**
 * Фиктивный менеджер соединения к БД
 * @author Alina
 * @version 1.0
 */
public class ConnectionManagerMock implements ConnectionManager {

    @Override
    public Connection getConnection() {
        return new ConnectionMock();
    }
}
