package data_access;

import entity.Course;
import entity.Task;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileCacheUserDataAccessObjectTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "test_userCache.json";

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
            Task task = new Task("task1", "description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
            user.addTask(task);
            fileCacheUserDAO.WriteToCache(user);
        });
    }

    @Test
    void testReadFromCache() {
        assertDoesNotThrow(() -> {
            User[] friends = {};
            Course[] courses = {new Course("CSC207", "Software Design")};
            User user = new User("user1", "password1", friends, courses);
            Task task = new Task("task1", "description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
            user.addTask(task);
            fileCacheUserDAO.WriteToCache(user);

            User readUser = fileCacheUserDAO.ReadFromCache("user1");
            assertNotNull(readUser);
            assertEquals(user.getUsername(), readUser.getUsername());
            assertEquals(user.getFriends().exportFriendsNames(), readUser.getFriends().exportFriendsNames());
            assertEquals(user.getCourses().get(0).getName(), readUser.getCourses().get(0).getName());
            assertEquals(user.getTodoList().getTasks().get(0).getTitle(), readUser.getTodoList().getTasks().get(0).getTitle());
        });
    }

    @Test
    void testReadFromCacheEmptyFile() {
        User readUser = fileCacheUserDAO.ReadFromCache("nonExistingUser");
        assertNull(readUser);
    }

    @Test
    void testUserExists() throws IOException {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        User user = new User("existingUser", "password", friends, courses);
        fileCacheUserDAO.WriteToCache(user);

        assertTrue(fileCacheUserDAO.UserExists("existingUser"));
        assertFalse(fileCacheUserDAO.UserExists("nonExistingUser"));
    }

    @Test
    void testFindByUsername() throws IOException {
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
