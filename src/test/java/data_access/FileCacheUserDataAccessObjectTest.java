package data_access;

import entity.Course;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FileCacheUserDataAccessObjectTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "test_userCache.txt";

    @BeforeEach
    void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testWriteToCache() {
        assertDoesNotThrow(() -> {
            User[] friends = {};
            Course[] courses = {new Course("CSC207", "Software Design")};
            User user = new User("user1", "password1", friends, courses);
            fileCacheUserDAO.WriteToCache(user);
        });
    }

    @Test
    void testReadFromCache() {
        assertDoesNotThrow(() -> {
            User[] friends = {};
            Course[] courses = {new Course("CSC207", "Software Design")};
            User user = new User("user1", "password1", friends, courses);
            fileCacheUserDAO.WriteToCache(user);

            User readUser = fileCacheUserDAO.ReadFromCache();
            assertNotNull(readUser);
            assertEquals(user.getUsername(), readUser.getUsername());
            assertEquals(user.getFriends().exportFriendsNames(), readUser.getFriends().exportFriendsNames());
            assertEquals(user.getCourses().get(0).getName(), readUser.getCourses().get(0).getName());
        });
    }

    @Test
    void testReadFromCacheEmptyFile() throws IOException, ClassNotFoundException {
        User readUser = fileCacheUserDAO.ReadFromCache();
        assertNull(readUser);
    }

    @Test
    void testUserExists() throws IOException, ClassNotFoundException {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        User user = new User("existingUser", "password", friends, courses);
        fileCacheUserDAO.WriteToCache(user);

        assertTrue(fileCacheUserDAO.UserExists("existingUser"));
        assertFalse(fileCacheUserDAO.UserExists("nonExistingUser"));
    }

    @Test
    void testFindByUsername() throws IOException, ClassNotFoundException {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        User user = new User("findUser", "password", friends, courses);
        fileCacheUserDAO.WriteToCache(user);

        User foundUser = fileCacheUserDAO.findByUsername("findUser");
        assertNotNull(foundUser);
        assertEquals("findUser", foundUser.getUsername());

        User nonExistingUser = fileCacheUserDAO.findByUsername("nonExistingUser");
        assertNull(nonExistingUser);
    }

    @Test
    void testFileCacheUserDataAccessObjectConstructorWithCustomPath() throws IOException {
        FileCacheUserDataAccessObject customPathDao = new FileCacheUserDataAccessObject(testFilePath);
        assertNotNull(customPathDao);
    }
}