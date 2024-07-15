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
    private File fileCache;

    @BeforeEach
    void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject();
        fileCache = new File(System.getProperty("user.dir") + "\\src\\main\\java\\data_access\\userCache.txt");
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the file after each test
        if (fileCache.exists()) {
            fileCache.delete();
        }
    }

    @Test
    void testWriteToCache() {
        assertDoesNotThrow(() -> {
            User[] friends = {};
            Course[] courses = { new Course("CSC207", "Software Design") };
            User user = new User("user1", "password1", friends, courses);
            fileCacheUserDAO.WriteToCache(user);
        });
    }

    @Test
    void testReadFromCache() {
        assertDoesNotThrow(() -> {
            User[] friends = {};
            Course[] courses = { new Course("CSC207", "Software Design") };
            User user = new User("user1", "password1", friends, courses);
            fileCacheUserDAO.WriteToCache(user);

            User readUser = fileCacheUserDAO.ReadFromCache();
            assertNotNull(readUser);
            assertEquals(user.getUsername(), readUser.getUsername());
            assertEquals(user.getFriends().exportFriendsNames(), readUser.getFriends().exportFriendsNames());
            assertEquals(user.getCourses().get(0).getName(), readUser.getCourses().get(0).getName());
        });
    }
}
