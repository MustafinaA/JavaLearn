package part1.lesson16.task01.dao.impl;

import part1.lesson16.task01.ConnectionManager.ConnectionManager;
import part1.lesson16.task01.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson16.task01.dao.UserRoleDao;
import part1.lesson16.task01.pojo.Role;
import part1.lesson16.task01.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс реализующий доступ к данным Роли Пользователя
 *
 * @author Alina
 * @version 1.0
 */
public class UserRoleDaoJdbcImpl implements UserRoleDao {

    private static ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();

    /**
     * Параметризированный запрос в добавлении записи
     *
     * @param user - добавляемый пользователь
     * @param role - добавляемая роль
     * @return - результат добавления записи в таблицу БД
     */
    @Override
    public boolean addUserRole(User user, Role role) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
