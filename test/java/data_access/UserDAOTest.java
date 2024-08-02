package data_access;

import entity.User;
import entity.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;
    private SQLDatabaseHelper dbHelper;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userDAO = new UserDAO(dbHelper);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Tasks");
            stmt.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testWriteToCacheAndReadFromCache() {
        User user = new User("TestUser", "TestPassword1", new User[]{}, new Course[]{});
        userDAO.WriteToCache(user);

        User retrievedUser = userDAO.ReadFromCache("TestUser");
        assertNotNull(retrievedUser);
        assertEquals("TestUser", retrievedUser.getUsername());
        assertTrue(retrievedUser.verifyPassword("TestPassword1"));
    }

    @Test
    void testUserExists() {
        User user = new User("TestUser", "TestPassword1", new User[]{}, new Course[]{});
        userDAO.WriteToCache(user);

        assertTrue(userDAO.UserExists("TestUser"));
        assertFalse(userDAO.UserExists("NonExistentUser"));
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("TestUser1", "TestPassword1", new User[]{}, new Course[]{});
        User user2 = new User("TestUser2", "TestPassword2", new User[]{}, new Course[]{});
        userDAO.WriteToCache(user1);
        userDAO.WriteToCache(user2);

        List<User> users = userDAO.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser() {
        User user = new User("TestUser", "TestPassword1", new User[]{}, new Course[]{});
        userDAO.WriteToCache(user);

        userDAO.deleteUser("TestUser");
        assertFalse(userDAO.UserExists("TestUser"));
    }
}
