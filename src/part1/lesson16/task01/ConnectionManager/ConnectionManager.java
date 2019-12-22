package part1.lesson16.task01.ConnectionManager;

import java.sql.Connection;

/**
 * Интерфейс соединения к БД
 * @author Alina
 * @version 1.0
 */
public interface ConnectionManager {
    Connection getConnection();
}
