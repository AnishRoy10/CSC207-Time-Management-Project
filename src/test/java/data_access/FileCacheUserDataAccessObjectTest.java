package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Course;
import entity.Task;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileCacheUserDataAccessObjectTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "test_userCache.json";
    private Gson gson;

    @BeforeEach
    void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .setPrettyPrinting()
                .create();
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
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
        User user = new User("user1", "password1", friends, courses);
        user.addTask(task);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("User written to cache: " + gson.toJson(user));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReadFromCache() {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
        User user = new User("user1", "password1", friends, courses);
        user.addTask(task);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("User written to cache: " + gson.toJson(user));

            User readUser = fileCacheUserDAO.ReadFromCache();
            assertNotNull(readUser);
            System.out.println("User read from cache: " + gson.toJson(readUser));

            assertEquals(user.getUsername(), readUser.getUsername());
            assertEquals(user.getFriends().exportFriendsNames(), readUser.getFriends().exportFriendsNames());
            assertEquals(user.getCourses().get(0).getName(), readUser.getCourses().get(0).getName());
            assertEquals(user.getTodoList().getTasks().get(0).getTitle(), readUser.getTodoList().getTasks().get(0).getTitle());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReadFromCacheEmptyFile() {
        try {
            User readUser = fileCacheUserDAO.ReadFromCache();
            assertNull(readUser);
            System.out.println("No user found in cache.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testUserExists() {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        User user = new User("existingUser", "password", friends, courses);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("User written to cache: " + gson.toJson(user));

            assertTrue(fileCacheUserDAO.UserExists("existingUser"));
            System.out.println("User exists in cache: existingUser");

            assertFalse(fileCacheUserDAO.UserExists("nonExistingUser"));
            System.out.println("User does not exist in cache: nonExistingUser");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testFindByUsername() {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        User user = new User("findUser", "password", friends, courses);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("User written to cache: " + gson.toJson(user));

            User foundUser = fileCacheUserDAO.findByUsername("findUser");
            assertNotNull(foundUser);
            System.out.println("User found by username: " + gson.toJson(foundUser));

            assertEquals("findUser", foundUser.getUsername());

            User nonExistingUser = fileCacheUserDAO.findByUsername("nonExistingUser");
            assertNull(nonExistingUser);
            System.out.println("User not found by username: nonExistingUser");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testFileCacheUserDataAccessObjectConstructorWithCustomPath() {
        try {
            FileCacheUserDataAccessObject customPathDao = new FileCacheUserDataAccessObject(testFilePath);
            assertNotNull(customPathDao);
            System.out.println("Custom path DAO initialized: " + testFilePath);
        }
        catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testSetReadAndChangeANumber() {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        User user = new User("user1", "password1", friends, courses);
        user.setANumber(5);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("Initial user written to cache: " + gson.toJson(user));

            // Read the user from cache
            User readUser = fileCacheUserDAO.findByUsername("user1");
            assertNotNull(readUser);
            System.out.println("User read from cache: " + gson.toJson(readUser));
            assertEquals(5, readUser.getANumber());

            // Change aNumber and write again
            readUser.setANumber(10);
            fileCacheUserDAO.WriteToCache(readUser);
            System.out.println("User after changing aNumber to 10: " + gson.toJson(readUser));

            // Read the user again
            User updatedUser = fileCacheUserDAO.findByUsername("user1");
            assertNotNull(updatedUser);
            System.out.println("User read from cache after update: " + gson.toJson(updatedUser));
            assertEquals(10, updatedUser.getANumber());

            // Change aNumber again and write
            updatedUser.setANumber(15);
            fileCacheUserDAO.WriteToCache(updatedUser);
            System.out.println("User after changing aNumber to 15: " + gson.toJson(updatedUser));

            // Read the user one last time
            User finalUser = fileCacheUserDAO.findByUsername("user1");
            assertNotNull(finalUser);
            System.out.println("User read from cache after final update: " + gson.toJson(finalUser));
            assertEquals(15, finalUser.getANumber());

        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteAndReadMultipleUsers() {
        User user1 = new User("user1", "password1", new User[]{}, new Course[]{new Course("CSC207", "Software Design")});
        User user2 = new User("user2", "password2", new User[]{}, new Course[]{new Course("CSC236", "Algorithms")});
        try {
            fileCacheUserDAO.WriteToCache(user1);
            fileCacheUserDAO.WriteToCache(user2);

            User readUser1 = fileCacheUserDAO.findByUsername("user1");
            User readUser2 = fileCacheUserDAO.findByUsername("user2");

            assertNotNull(readUser1);
            assertNotNull(readUser2);
            assertEquals("user1", readUser1.getUsername());
            assertEquals("user2", readUser2.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testUpdateUserData() {
        User user = new User("userToUpdate", "password", new User[]{}, new Course[]{new Course("CSC207", "Software Design")});
        try {
            fileCacheUserDAO.WriteToCache(user);

            // Update user data
            user.setPassword("newPassword");
            fileCacheUserDAO.WriteToCache(user);

            User updatedUser = fileCacheUserDAO.findByUsername("userToUpdate");
            assertNotNull(updatedUser);
            assertEquals("newPassword", updatedUser.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAddAndRemoveTask() {
        User user = new User("userWithTasks", "password", new User[]{}, new Course[]{new Course("CSC207", "Software Design")});
        Task task = new Task("Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
        user.addTask(task);
        try {
            fileCacheUserDAO.WriteToCache(user);

            User readUser = fileCacheUserDAO.findByUsername("userWithTasks");
            assertNotNull(readUser);
            assertEquals(1, readUser.getTodoList().getTasks().size());

            readUser.removeTask(task);
            fileCacheUserDAO.WriteToCache(readUser);

            User updatedUser = fileCacheUserDAO.findByUsername("userWithTasks");
            assertNotNull(updatedUser);
            assertEquals(0, updatedUser.getTodoList().getTasks().size());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }
}
