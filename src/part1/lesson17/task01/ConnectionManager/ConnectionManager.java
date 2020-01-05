package part1.lesson17.task01.ConnectionManager;

import java.sql.Connection;

/**
 * Интерфейс соединения к БД
 * @author Alina
 * @version 1.0
 */
public interface ConnectionManager {
    Connection getConnection();
}
