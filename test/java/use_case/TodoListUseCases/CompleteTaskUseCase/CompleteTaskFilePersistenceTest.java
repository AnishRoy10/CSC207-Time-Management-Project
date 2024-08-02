package use_case.TodoListUseCases.CompleteTaskUseCase;

import data_access.FileCacheLeaderboardDataAccessObject;
import data_access.SQLDatabaseHelper;
import data_access.TaskDAO;
import data_access.UserDAO;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;
import repositories.TaskRepository;
import repositories.UserRepository;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CompleteTaskFilePersistenceTest {
    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private LeaderboardRepository leaderboardRepository;
    private CompleteTaskOutputBoundary completeTaskOutputBoundary;
    private CompleteTaskUseCase completeTaskUseCase;
    private User user;
    private Task task;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    public void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userRepository = new UserDAO(dbHelper);
        taskRepository = new TaskDAO(dbHelper);
        try {
            leaderboardRepository = new FileCacheLeaderboardDataAccessObject("test_leaderboards.json");
        } catch (Exception e) {
            System.out.println("Failed to create leaderboard repository: " + e.getMessage());
            fail("Failed to create leaderboard repository: " + e.getMessage());
        }
        completeTaskOutputBoundary = responseModel -> {
            // No-op implementation for testing
        };
        completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, completeTaskOutputBoundary, leaderboardRepository);

        user = new User("testUser", "password", new User[0], new Course[0]);
        task = new Task("testUser", "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Test Course");
        user.getTodoList().addTask(task);
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }
        try {
            taskRepository.WriteToCache(task, user.getUsername());
        } catch (Exception e) {
            System.out.println("Failed to save task: " + e.getMessage());
            fail("Exception thrown while saving task: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Tasks");
            stmt.execute("DELETE FROM Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testTaskPointsAwardedPersistence() {
        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(task.getId(), "testUser");

        assertFalse(task.isPointsAwarded());
        assertFalse(task.isCompleted());

        // Reload task from the database to verify persistence
        Task savedTask2;
        try {
            savedTask2 = taskRepository.ReadFromCache(task.getId());
        } catch (Exception e) {
            System.out.println("Failed to retrieve task: " + e.getMessage());
            fail("Exception thrown while retrieving task: " + e.getMessage());
            return;
        }

        assertFalse(savedTask2.isCompleted());
        assertFalse(savedTask2.isPointsAwarded());

        // Complete the task
        completeTaskUseCase.execute(requestModel);

        // Reload task from the database to verify persistence
        Task savedTask1;
        try {
            savedTask1 = taskRepository.ReadFromCache(task.getId());
        } catch (Exception e) {
            System.out.println("Failed to retrieve task: " + e.getMessage());
            fail("Exception thrown while retrieving task: " + e.getMessage());
            return;
        }

        assertTrue(savedTask1.isCompleted());
        assertTrue(savedTask1.isPointsAwarded());
    }
}
