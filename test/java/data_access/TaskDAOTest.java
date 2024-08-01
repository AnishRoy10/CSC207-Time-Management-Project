package data_access;

import entity.Task;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskDAOTest {
    private static SQLDatabaseHelper dbHelper;
    private static TaskDAO taskDAO;

    @BeforeAll
    static void setup() {
        dbHelper = new SQLDatabaseHelper();
        dbHelper.initializeDatabase();
        taskDAO = new TaskDAO(dbHelper);
    }

    @BeforeEach
    void clearDatabase() {
        try (var conn = dbHelper.connect();
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Tasks");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testWriteAndReadTask() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task(taskId, "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Test Course");
        String username = "testuser";

        taskDAO.WriteToCache(task, username);
        Task readTask = taskDAO.ReadFromCache(taskId);

        assertNotNull(readTask);
        assertEquals(task.getId(), readTask.getId());
        assertEquals(task.getTitle(), readTask.getTitle());
        assertEquals(task.getDescription(), readTask.getDescription());
        assertEquals(task.getStartDate(), readTask.getStartDate());
        assertEquals(task.getDeadline(), readTask.getDeadline());
        assertEquals(task.getCourse(), readTask.getCourse());
        assertEquals(task.isCompleted(), readTask.isCompleted());
        assertEquals(task.isPointsAwarded(), readTask.isPointsAwarded());
    }

    @Test
    void testGetAllTasks() {
        UUID taskId1 = UUID.randomUUID();
        Task task1 = new Task(taskId1, "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        UUID taskId2 = UUID.randomUUID();
        Task task2 = new Task(taskId2, "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");
        String username = "testuser";

        taskDAO.WriteToCache(task1, username);
        taskDAO.WriteToCache(task2, username);
        List<Task> tasks = taskDAO.getAllTasks(username);

        assertNotNull(tasks);
        assertEquals(2, tasks.size());

        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

    @Test
    void testDeleteTask() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task(taskId, "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Test Course");
        String username = "testuser";

        taskDAO.WriteToCache(task, username);
        taskDAO.deleteTask(taskId);
        Task deletedTask = taskDAO.ReadFromCache(taskId);

        assertNull(deletedTask);
    }
}
