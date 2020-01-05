package part1.lesson17.task01.pojo;

/**
 * Класс описание таблицы Роль
 *
 * @author Alina
 * @version 1.0
 */
public class Role {
    public static final String TABLE_NAME = "study.public.role";

    private Integer id;
    private String name;
    private String description;

    public Role(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
