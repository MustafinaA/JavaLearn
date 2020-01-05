package part1.lesson17.task01.pojo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (login_id != null ? !login_id.equals(user.login_id) : user.login_id != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return description != null ? description.equals(user.description) : user.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (login_id != null ? login_id.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
