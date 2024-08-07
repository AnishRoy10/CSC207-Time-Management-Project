package use_case.TodoListUseCases.SortTasksUseCase;

import data_access.SQLDatabaseHelper;
import data_access.UserDAO;
import entity.Course;
import entity.User;
import entity.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TaskRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortTasksUseCaseTest2 {
    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userRepository = new UserDAO(dbHelper) {
            @Override
            public User findByUsername(String username) throws IOException {
                throw new IOException("Database read error");
            }
        };
        taskRepository = new TaskRepository() {
            @Override
            public void WriteToCache(Task task, String username) { }

            @Override
            public void WriteToCache(Task task, String username, String course) { }

            @Override
            public Task ReadFromCache(UUID id) {
                return null;
            }

            @Override
            public List<Task> getAllTasks(String username) {
                return null;
            }

            @Override
            public List<Task> getAllTasks(String username, String courseName) {
                return null;
            }

            @Override
            public void deleteTask(UUID id) { }
        };
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
    void testSortTasksIOException() {
        // Create and save the user
        User user = new User("sortUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        SortTasksOutputBoundary sortTasksOutputBoundary = responseModel -> {
            // No-op
        };
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, sortTasksOutputBoundary);

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("completion", true, "sortUser");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sortTasksUseCase.execute(sortRequestModel));
        assertEquals("Database error", exception.getMessage());
        assertEquals("Database read error", exception.getCause().getMessage());
    }
}