package part1.lesson16.task01.dao.impl;

import part1.lesson16.task01.ConnectionManager.ConnectionManager;
import part1.lesson16.task01.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson16.task01.dao.RoleDao;
import part1.lesson16.task01.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс реализующий доступ к данным Роли
 *
 * @author Alina
 * @version 1.0
 */
public class RoleDaoJdbcImpl implements RoleDao {

    private static ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();

    /**
     * Параметризированный запрос в добавлении записи
     *
     * @param role добавляемая роль
     * @return результат добавления записи в таблицу БД
     */
    @Override
    public boolean addRole(Role role) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getDescription());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
