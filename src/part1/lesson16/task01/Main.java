package part1.lesson16.task01;

//import ch.qos.logback.classic.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part1.lesson15.task02.ConnectionManager.ConnectionManager;
import part1.lesson15.task02.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson15.task02.dao.UserDao;
import part1.lesson15.task02.dao.impl.UserDaoJdbcImpl;
import part1.lesson15.task02.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
Взять за основу ДЗ_15,
покрыть код логированием
в основных блоках try покрыть уровнем INFO
с исключениях catch покрыть уровнем ERROR
настроить конфигурацию логера, что бы все логи записывались в БД, таблица LOGS,
колонки ID, DATE, LOG_LEVEL, MESSAGE, EXCEPTION
 */

public class Main {
    private static String SQL;
   // private static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Main.class.getName());
   private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("start program");
        UserDao userDao = new UserDaoJdbcImpl();

        User user = new User(null, "userF03", new Date(new java.util.Date().getTime()), "F03_login", "Moscow", "f3@dmail.ru", "anyDescription");
        userDao.addUser(user);

        List<User> adderUsers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            adderUsers.add(
                    new User(
                            null,
                            "userF0" + i,
                            new Date(new java.util.Date().getTime()),
                            "F0" + i + "_login",
                            "Moscow",
                            "f0" + i + "@dmail.ru",
                            "anyDescription"));
        }
        userDao.addUser(adderUsers);

        userDao.getUserByLoginId_Name("F02_login", "userF02").forEach(System.out::println);

        ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();
        Connection connection = connectionManager.getConnection();
        Statement statement = null;
        Savepoint savepoint = null;
        try {
            statement = connection.createStatement();
            logger.info("create Statement");
            connection.setAutoCommit(false);
            SQL = "INSERT INTO study.public.user VALUES " +
                    "(DEFAULT, 'TestUser', '12.12.2019', 'testUser', 'testCity', 'testEmail', 'testDescription')";
            statement.executeUpdate(SQL);
            logger.info("execute Update 1");
            savepoint = connection.setSavepoint("SavepointA");
            SQL = "INSERT INTO study.public.role VALUES (DEFAULT, 'a', 'ghg')";
            statement.executeUpdate(SQL);
            logger.info("execute Update 2");
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            try {
                if (savepoint == null) {
                    connection.rollback();
                    logger.info("Транзакции в JDBC откатить");
                } else {
                    connection.rollback(savepoint);
                    connection.commit();
                }
            } catch (SQLException e1) {
                logger.error("SQLException во время отката " + e1.getMessage());
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                    connection.close();
                    logger.info("connection closed");
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }
}
