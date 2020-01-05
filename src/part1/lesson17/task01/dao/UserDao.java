package part1.lesson17.task01.dao;

import part1.lesson17.task01.pojo.User;

import java.util.List;

/**
 * Интерфейс доступа к данным Пользователь
 *
 * @author Alina
 * @version 1.0
 */
public interface UserDao {
    String SQL_FIND_ALL = "SELECT * FROM " + User.TABLE_NAME;
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE " + User.LOGIN_ID_COLUMN + " = ? AND " + User.NAME_COLUMN + "=?";
    String SQL_INSERT = "INSERT INTO " + User.TABLE_NAME + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    String SQL_UPDATE = "UPDATE " + User.TABLE_NAME + " SET name=?, birthday=?, login_id=?, city=?, email=?, description=? WHERE id=?";
    String SQL_DELETE = "DELETE FROM " + User.TABLE_NAME + " WHERE id=?";

    boolean addUser(User user);
    List<User> getUserByLoginId_Name(String login_ID, String name);
    void updateUserById(User user);
    boolean deleteUserById(int id);
}
