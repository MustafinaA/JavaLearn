package part1.lesson16.task01.dao;

import part1.lesson16.task01.pojo.User;

import java.util.List;
/**
 * Интерфейс доступа к данным Пользователь
 *
 * @author Alina
 * @version 1.0
 */
public interface UserDao {
    String SQL_FIND_ALL = "select * from " + User.TABLE_NAME;
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where " + User.LOGIN_ID_COLUMN + " = ? and " + User.NAME_COLUMN + "=?";
    String SQL_INSERT = "insert into " + User.TABLE_NAME + " values (DEFAULT, ?, ?, ?, ?, ?, ?)";

    boolean addUser(User user);

    boolean addUser(List<User> user);

    List<User> getUserByLoginId_Name(String login_ID, String name);
}
