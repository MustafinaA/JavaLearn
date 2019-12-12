package part1.lesson15.task02;

import part1.lesson15.task02.ConnectionManager.ConnectionManager;
import part1.lesson15.task02.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson15.task02.dao.UserDao;
import part1.lesson15.task02.dao.impl.UserDaoJdbcImpl;
import part1.lesson15.task02.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static String SQL;

    public static void main(String[] args) {
        UserDao userDao = new UserDaoJdbcImpl();

//      2)      Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
//         a)      Используя параметризированный запрос
        User user = new User(null, "userF03", new Date(new java.util.Date().getTime()), "F03_login", "Moscow", "f3@dmail.ru", "anyDescription");
        userDao.addUser(user);

//         b)      Используя batch процесс
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

//      3)      Сделать параметризированную выборку по login_ID и name одновременно
        userDao.getUserByLoginId_Name("F02_login", "userF02").forEach(System.out::println);

//      4)      Перевести connection в ручное управление транзакциями
//        a)    Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
//              между sql операциями установить логическую точку сохранения(SAVEPOINT)
//        б)    Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
//              между sql операциями установить точку сохранения (SAVEPOINT A),
//              намеренно ввести некорректные данные на последней операции,
//              что бы транзакция откатилась к логической точке SAVEPOINT A
        ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();
        Connection connection = connectionManager.getConnection();
        Statement statement = null;
        Savepoint savepoint = null;
        try {
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            SQL = "INSERT INTO study.public.user VALUES " +
                    "(DEFAULT, 'TestUser', '12.12.2019', 'testUser', 'testCity', 'testEmail', 'testDescription')";
            statement.executeUpdate(SQL);
            savepoint = connection.setSavepoint("SavepointA");
            SQL = "INSERT INTO study.public.role VALUES (DEFAULT, 'a', 'ghg')";
            statement.executeUpdate(SQL);
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("SQLException. Выполнение отката в точку сохранения...");
            try {
                if (savepoint == null) {
                    connection.rollback();
                    System.out.println("Транзакции в JDBC откатить");
                } else {
                    connection.rollback(savepoint);
                    connection.commit();
                }
            } catch (SQLException e1) {
                System.out.println("SQLException во время отката " + e.getMessage());
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
