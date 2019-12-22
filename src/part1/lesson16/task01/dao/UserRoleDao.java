package part1.lesson16.task01.dao;

import part1.lesson16.task01.pojo.Role;
import part1.lesson16.task01.pojo.User;
import part1.lesson16.task01.pojo.UserRole;

/**
 * Интерфейс доступа к данным Роль Пользователя
 *
 * @author Alina
 * @version 1.0
 */
public interface UserRoleDao {
    String SQL_INSERT = "insert into " + UserRole.TABLE_NAME + " values (DEFAULT, ?, ?)";

    boolean addUserRole(User user, Role role);
}
