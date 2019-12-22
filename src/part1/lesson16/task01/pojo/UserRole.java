package part1.lesson16.task01.pojo;

/**
 * Класс описание таблицы Роль Пользователя
 *
 * @author Alina
 * @version 1.0
 */
public class UserRole {
    public static final String TABLE_NAME = "study.public.user_role";

    private Integer id;
    private Integer userId;
    private Integer roleId;

    public UserRole(Integer id, Integer userId, Integer roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
