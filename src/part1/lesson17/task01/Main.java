package part1.lesson17.task01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part1.lesson17.task01.ConnectionManager.ConnectionManager;
import part1.lesson17.task01.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson17.task01.dao.UserDao;
import part1.lesson17.task01.dao.impl.UserDaoJdbcImpl;
import part1.lesson17.task01.pojo.User;

import java.sql.*;
import java.util.List;

/*
Задание1
Взять за основу ДЗ_15. Написать тест на CRUD операции
Инициализацию соединение с БД произвести один раз перед началом тестов, после завершения всех тестов, закрыть соединие с БД
Подготовку данных для CRUD операций производить в методе Init используя @Before
 */
/**
 * Класс с методами для тестирования инструментами JUnit5
 * @author Alina
 * @version 1.0
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();
        UserDao userDao = new UserDaoJdbcImpl(connectionManager);
        Main main = new Main();
        User user = new User(0, "u17", new Date(new java.util.Date().getTime()), "17_login", "M", "17@dmail.ru", "anyDescr");
        main.CRUDForTesting(userDao, user);
    }

    public void CRUDForTesting(UserDao userDao, User user) {
        insert(userDao, user);
        user = select(userDao, user);
        update(userDao, user, "u17_0");
        delete(userDao, user);
    }

    public void insert(UserDao userDao, User user) {
        userDao.addUser(user);
        LOGGER.info("INSERT: {}", user);
    }

    public User select(UserDao userDao, User user) {
        List<User> lUsers = userDao.getUserByLoginId_Name(user.getLogin_id(), user.getName());
        user = lUsers.get(0);
        LOGGER.info("SELECT: {}", user);
        return user;
    }

    public boolean update(UserDao userDao, User user, String newName) {
        boolean result = false;
        user.setName(newName);
        userDao.updateUserById(user);
        List<User> lUsers = userDao.getUserByLoginId_Name(user.getLogin_id(), user.getName());
        if (lUsers != null && lUsers.size() > 0) {
            result = true;
            user = lUsers.get(0);
            LOGGER.info("UPDATE: {}", user);
        }
        return result;
    }

    public boolean delete(UserDao userDao, User user) {
        boolean deleted = userDao.deleteUserById(user.getId());
        LOGGER.info("DELETE: {}", deleted);
        return deleted;
    }
}
