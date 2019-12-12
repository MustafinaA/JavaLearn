package part1.lesson15.task01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/*
1)    Спроектировать базу
-      Таблица USER содержит поля id, name, birthday, login_ID, city, email, description
-      Таблица ROLE содержит поля id, name (принимает значения Administration, Clients, Billing), description
-      Таблица USER_ROLE содержит поля id, user_id, role_id
 */

/**
 * Создаются 3 таблицы (Пользователь, Роль, РольПользователя) ля дальнейшей работы с ними,
 * а также вспомогательный тип перечисление, чтобы ограничить вводимые значения в поле таблицы Роль
 * @author Alina
 * @version 1.0
 */
public class jdbcDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/study", "postgres", "qwerty");
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS study.public.USER; "
                    + "CREATE TABLE study.public.USER (\n"
                    + "    id bigserial not null primary key,\n"
                    + "    name varchar(100) NOT NULL,\n"
                    + "    birthday date,\n"
                    + "    login_id varchar(100) NOT NULL,\n"
                    + "    city     varchar(25),\n"
                    + "    email varchar(25) NOT NULL,\n"
                    + "    description varchar(255));");
            statement.execute("DROP TABLE IF EXISTS study.public.ROLE;"
                    + "DROP TYPE IF EXISTS role_type;"
                    + "CREATE TYPE role_type AS ENUM('Administration', 'Clients', 'Billing');"
                    + "CREATE TABLE study.public.ROLE (\n"
                    + "    id bigserial not null primary key,\n"
                    + "    name role_type NOT NULL,\n"
                    + "    description varchar(255));");
            statement.execute("DROP TABLE IF EXISTS study.public.USER_ROLE;"
                    + "CREATE TABLE study.public.USER_ROLE (\n"
                    + "    id bigserial not null primary key,\n"
                    + "    user_id integer NOT NULL,\n"
                    + "    role_id integer NOT NULL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
