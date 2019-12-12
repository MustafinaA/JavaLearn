package part1.lesson15.task02.dao;

import part1.lesson15.task02.pojo.Role;
import part1.lesson15.task02.pojo.User;
import part1.lesson15.task02.pojo.UserRole;

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
