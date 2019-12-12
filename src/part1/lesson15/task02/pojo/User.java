package part1.lesson15.task02.pojo;

import java.sql.Date;

/**
 * Класс описание таблицы Пользователь
 *
 * @author Alina
 * @version 1.0
 */
public class User {
    public static final String TABLE_NAME = "study.public.user";
    public static final String LOGIN_ID_COLUMN = "login_id";
    public static final String NAME_COLUMN = "name";

    private Integer id;
    private String name;
    private Date birthday;
    private String login_id;
    private String city;
    private String email;
    private String description;

    public User(Integer id, String name, Date birthday, String login_id, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.login_id = login_id;
        this.city = city;
        this.email = email;
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

    public Date getBirthday() {
        return birthday;
    }

    public String getLogin_id() {
        return login_id;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", login_id='" + login_id + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
