package part1.lesson16.task01.dao;

import part1.lesson16.task01.pojo.Role;
/**
 * Интерфейс доступа к данным Роль
 *
 * @author Alina
 * @version 1.0
 */
public interface RoleDao {
    String SQL_INSERT = "insert into " + Role.TABLE_NAME + " values (DEFAULT, ?, ?)";

    boolean addRole(Role role);
}
