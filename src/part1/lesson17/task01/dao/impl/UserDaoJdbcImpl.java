package part1.lesson17.task01.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part1.lesson17.task01.ConnectionManager.ConnectionManager;
import part1.lesson17.task01.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson17.task01.pojo.User;
import part1.lesson17.task01.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализующий доступ к данным Пользователи
 *
 * @author Alina
 * @version 1.0
 */
public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJdbcImpl.class);

    private static ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();

    public UserDaoJdbcImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    /**
     * Параметризированный запрос в добавлении записи
     * @param user - добавляемый пользователь
     * @return - результат добавления записи в таблицу БД
     */
    @Override
    public boolean addUser(User user) {
        try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setDate(2, user.getBirthday());
            preparedStatement.setString(3, user.getLogin_id());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            LOGGER.info("added:"+preparedStatement.executeUpdate());
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
            if (resultSet.next()) {
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

    @Override
    public void updateUserById(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setDate(2, user.getBirthday());
            preparedStatement.setString(3, user.getLogin_id());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            preparedStatement.setInt(7, user.getId());

            LOGGER.info("updated:"+preparedStatement.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteUserById(int id) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, id);
            LOGGER.info("deleted:"+preparedStatement.executeUpdate());
            result = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
