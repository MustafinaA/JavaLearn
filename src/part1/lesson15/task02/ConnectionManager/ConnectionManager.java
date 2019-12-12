package part1.lesson15.task02.ConnectionManager;

import java.sql.Connection;

/**
 * Интерфейс соединения к БД
 * @author Alina
 * @version 1.0
 */
public interface ConnectionManager {
    Connection getConnection();
}
