package data_access;

import entity.Course;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FriendsListDataAccessObjectTest {
    private SQLDatabaseHelper dbHelper;
    private FriendsListDataAccessObject dao;

    @BeforeEach
    void setUp() throws IOException {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:saves/TestDB.db");
        dbHelper.initializeDatabase();
        dao = new FriendsListDataAccessObject(dbHelper);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriteUser() throws IOException {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        dao.writeUser(user);

        User loadedUser = dao.loadUser("testUser");
        assertEquals(user.getUsername(), loadedUser.getUsername());
        assertEquals(user.getPassword(), loadedUser.getPassword());
        assertEquals(0, loadedUser.getFriends().exportFriendsNames().size());
    }

    @Test
    void testWriteUserWithFriends() throws IOException {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User friend1 = new User("friend1", "password", new User[]{}, new Course[]{});
        User friend2 = new User("friend2", "password", new User[]{}, new Course[]{});
        user.addFriend(friend1);
        user.addFriend(friend2);

        // Ensure friends are written to the database before the user
        dao.writeUser(friend1);
        dao.writeUser(friend2);
        dao.writeUser(user);

        User loadedUser = dao.loadUser("testUser");
        assertEquals(user.getUsername(), loadedUser.getUsername());
        assertEquals(2, loadedUser.getFriends().exportFriendsNames().size());
        assertEquals("friend1", loadedUser.getFriends().exportFriendsNames().get(0));
        assertEquals("friend2", loadedUser.getFriends().exportFriendsNames().get(1));
    }

    @Test
    void testLoadNonexistentUser() {
        Exception exception = assertThrows(IOException.class, () -> dao.loadUser("nonexistentUser"));
        assertEquals("User not found", exception.getMessage());
    }
}
