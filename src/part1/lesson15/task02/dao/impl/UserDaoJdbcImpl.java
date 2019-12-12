package part1.lesson15.task02.dao.impl;

import part1.lesson15.task02.ConnectionManager.ConnectionManager;
import part1.lesson15.task02.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson15.task02.dao.UserDao;
import part1.lesson15.task02.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс реализующий доступ к данным Пользователи
 *
 * @author Alina
 * @version 1.0
 */
public class UserDaoJdbcImpl implements UserDao {

    private static ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();

    /**
     * Параметризированный запрос в добавлении записи
     * @param user - добавляемый пользователь
     * @return - результат добавления записи в таблицу БД
     */
    @Override
    public boolean addUser(User user) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setDate(2, user.getBirthday());
            preparedStatement.setString(3, user.getLogin_id());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * batch процесс
     * @param user - список пользователей, которые необходимо добавить в таблицу
     * @return количество добавленных записей
     */
    @Override
    public boolean addUser(List<User> user) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement batchStatement = connection.prepareStatement(SQL_INSERT);
            for (User userItem: user){
                batchStatement.setString(1, userItem.getName());
                batchStatement.setDate(2, userItem.getBirthday());
                batchStatement.setString(3, userItem.getLogin_id());
                batchStatement.setString(4, userItem.getCity());
                batchStatement.setString(5, userItem.getEmail());
                batchStatement.setString(6, userItem.getDescription());
                batchStatement.addBatch();
            }
            System.out.println(batchStatement.executeBatch());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Поиск записей, удовлетворяющих заданному условию
     * @param login_ID логин
     * @param name имя пользователя
     * @return найденный пользователь
     */
    @Override
    public List<User> getUserByLoginId_Name(String login_ID, String name) {
        List<User> listUser = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
            preparedStatement.setString(1, login_ID);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listUser.add(new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUser;
    }
}
