package data_access;

import entity.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskDAOTest {
    private SQLDatabaseHelper dbHelper;
    private TaskDAO taskDAO;
    private final String dbPath = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper(dbPath);
        dbHelper.initializeDatabase();
        taskDAO = new TaskDAO(dbHelper);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Tasks");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriteToCacheAndReadFromCache() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task("testUser", "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        task.setId(taskId);
        taskDAO.WriteToCache(task, "testUser");

        Task retrievedTask = taskDAO.ReadFromCache(taskId);
        assertNotNull(retrievedTask);
        assertEquals(task.getId(), retrievedTask.getId());
        assertEquals(task.getTitle(), retrievedTask.getTitle());
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task("testUser", "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        Task task2 = new Task("testUser", "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");
        taskDAO.WriteToCache(task1, "testUser");
        taskDAO.WriteToCache(task2, "testUser");

        List<Task> tasks = taskDAO.getAllTasks("testUser");
        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Task 1")));
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Task 2")));
    }

    @Test
    void testDeleteTask2() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task("testUser", "Task to Delete", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        task.setId(taskId);
        taskDAO.WriteToCache(task, "testUser");

        taskDAO.deleteTask(taskId);
        Task retrievedTask = taskDAO.ReadFromCache(taskId);
        assertNull(retrievedTask);
    }


    @Test
    void testAddAndRetrieveTask() {
        Task task = new Task("testUser", "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        taskDAO.WriteToCache(task, "testUser");

        Task retrievedTask = taskDAO.ReadFromCache(task.getId());

        assertNotNull(retrievedTask);
        assertEquals("Test Task", retrievedTask.getTitle());
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("testUser", "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        taskDAO.WriteToCache(task, "testUser");

        // Update the task's title
        task.setTitle("Updated Task");
        taskDAO.WriteToCache(task, "testUser");

        Task retrievedTask = taskDAO.ReadFromCache(task.getId());

        assertNotNull(retrievedTask);
        assertEquals("Updated Task", retrievedTask.getTitle());
    }

    @Test
    void testGetAllTasksForUser() {
        Task task1 = new Task("testUser", "Test Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        Task task2 = new Task("testUser", "Test Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), null);
        taskDAO.WriteToCache(task1, "testUser");
        taskDAO.WriteToCache(task2, "testUser");

        List<Task> tasks = taskDAO.getAllTasks("testUser");

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Test Task 1")));
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Test Task 2")));
    }

    @Test
    void testDeleteTask() {
        Task task = new Task("testUser", "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        taskDAO.WriteToCache(task, "testUser");

        taskDAO.deleteTask(task.getId());

        Task retrievedTask = taskDAO.ReadFromCache(task.getId());

        assertNull(retrievedTask);
    }

    @Test
    void testAddAndRetrieveTaskWithCourseName() {
        Task task = new Task("testUser", "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        taskDAO.WriteToCache(task, "testUser", "CourseName");

        Task retrievedTask = taskDAO.ReadFromCache(task.getId());

        assertNotNull(retrievedTask);
        assertEquals("Test Task", retrievedTask.getTitle());
        assertEquals("Course", retrievedTask.getCourse());
    }

    @Test
    void testGetAllTasksForCourseName() {
        Task task1 = new Task("testUser", "Test Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        Task task2 = new Task("testUser", "Test Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course");
        taskDAO.WriteToCache(task1, "testUser", "CourseName");
        taskDAO.WriteToCache(task2, "testUser", "CourseName");

        List<Task> tasks = taskDAO.getAllTasks("testUser", "CourseName");

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Test Task 1")));
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Test Task 2")));
    }
}
