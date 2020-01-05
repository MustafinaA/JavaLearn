package part1.lesson17.test;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part1.lesson17.task01.ConnectionManager.ConnectionManager;
import part1.lesson17.task01.Main;
import part1.lesson17.task01.dao.UserDao;
import part1.lesson17.task01.dao.impl.UserDaoJdbcImpl;
import part1.lesson17.task01.pojo.User;
import part1.lesson17.test.mocks.ConnectionManagerMock;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
/**
 * Набор тестов на класс Main
 * @author Alina
 * @version 1.0
 */
class MainTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    private static Main main;
    private static UserDao userDao;
    private static ConnectionManager connectionManager;
    private static User user;

    @BeforeAll
    static void tearDownAll() {
        LOGGER.info("BeforeEach in MainTest");
        main = new Main();
        connectionManager = new ConnectionManagerMock();// фиктивный менеджер соединения
        userDao = new UserDaoJdbcImpl(connectionManager);
    }

    @AfterAll
    static void setUpAll() throws SQLException {
        LOGGER.info("AfterAll in MainTest");
        connectionManager.getConnection().close();
    }

    @BeforeEach
    void setUp() {
        LOGGER.trace("BeforeEach in MainTest");
        user = new User(0, "u17", new Date(new java.util.Date().getTime()), "17_login", "M", "17@dmail.ru", "anyDescr");
    }

    @Test
    @DisplayName("для CRUD когда всё ок")
    void main() {
        assumeTrue(main!=null);
        assertDoesNotThrow(() -> main.CRUDForTesting(userDao, user ));
    }

    @Test
    @DisplayName("для CRUD с npe")
    void mainWithException() {
        assertThrows(NullPointerException.class, () -> main.CRUDForTesting(null, user));
    }

    @Test
    @DisplayName("для insert")
    void insert() {
        assumeTrue(user!=null);
        assertDoesNotThrow(() -> main.insert(userDao, user ));
    }

    @Test
    @DisplayName("для select")
    void select() {
        assumeTrue(user!=null);
        assertDoesNotThrow(() -> main.select(userDao, user));
    }

    @Test
    @DisplayName("для update")
    void update() {
        assumeTrue(user!=null);
        assertEquals(true, main.update(userDao, user, "someName"));
    }

    @Test
    @DisplayName("TEST для select return")
    void selectReturnEquals() {
        assumeTrue(user!=null);
        assertEquals(user, main.select(userDao, user));
    }

    @Test
    @DisplayName("для delete")
    void delete() {
        assumeTrue(user!=null);
        assertEquals(true, main.delete(userDao, user));
    }
}