package part1.lesson17.test.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import part1.lesson17.task01.ConnectionManager.ConnectionManager;
import part1.lesson17.task01.ConnectionManager.ConnectionManagerJdbcImpl;
import part1.lesson17.task01.dao.UserDao;
import part1.lesson17.task01.dao.impl.UserDaoJdbcImpl;
import part1.lesson17.task01.pojo.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/*
Задание 2
Используя Spy и Mockito создать заглушки для интерфейса JDBC
 */
public class UserDaoJdbcImplTest {
    private UserDao userDao;
    private ConnectionManager connectionManager;
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        //В режиме слежения
        connectionManager = Mockito.spy(ConnectionManagerJdbcImpl.getInstance());
        connection = Mockito.mock(Connection.class);
        userDao = new UserDaoJdbcImpl(connectionManager);
    }

    @Test
    void addUser() throws SQLException {
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.doReturn(preparedStatement).when(connection).prepareStatement(UserDaoJdbcImpl.SQL_INSERT);
        String name = "u17";
        Date birthday = new Date(new java.util.Date().getTime());
        String login_id = "17_login";
        String city = "M";
        String email = "17@dmail.ru";
        String description = "anyDescr";
        User user = new User(0, name, birthday, login_id, city, email, description);

        boolean result = userDao.addUser(user);

        Mockito.verify(connectionManager, Mockito.times(1)).getConnection();
        Mockito.verify(connection, Mockito.times(1)).prepareStatement(UserDaoJdbcImpl.SQL_INSERT);
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(1, name);
        Mockito.verify(preparedStatement, Mockito.times(1)).setDate(2, birthday);
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(3, login_id);
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(4, city);
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(5, email);
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(6, description);
        Assertions.assertAll(() -> Assertions.assertTrue(result));
    }
}
