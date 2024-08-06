package app.gui;

import entity.Task;
import data_access.*;
import repositories.TaskRepository;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class TodoListInitializerTest {
    private SQLDatabaseHelper dbHelper;
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:Saves/TestDB.db");
        dbHelper.initializeDatabase();
        taskRepository = new TaskDAO(dbHelper);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Tasks");
            stmt.execute("DELETE FROM Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testDistinguishUserAndCourseTodoLists() {
        String username = "testUser";
        String courseName = "testCourse";

        // Add tasks to the user's personal to-do list
        Task userTask = new Task(username, "User Task", "User Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), null);
        taskRepository.WriteToCache(userTask, username);

        // Add tasks to the course-specific to-do list
        Task courseTask = new Task(username, "Course Task", "Course Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), courseName);
        taskRepository.WriteToCache(courseTask, username);

        // Retrieve and verify tasks for the user's personal to-do list
        List<Task> userTasks = taskRepository.getAllTasks(username);
        assertEquals(1, userTasks.size());
        assertEquals("User Task", userTasks.get(0).getTitle());

        // Retrieve and verify tasks for the course-specific to-do list
        List<Task> courseTasks = taskRepository.getAllTasks(username, courseName);
        assertEquals(1, courseTasks.size());
        assertEquals("Course Task", courseTasks.get(0).getTitle());
    }
}
