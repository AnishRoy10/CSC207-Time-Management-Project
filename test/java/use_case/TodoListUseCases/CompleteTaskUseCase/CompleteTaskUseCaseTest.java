package use_case.TodoListUseCases.CompleteTaskUseCase;

import data_access.FileCacheLeaderboardDataAccessObject;
import data_access.SQLDatabaseHelper;
import data_access.TaskDAO;
import data_access.UserDAO;
import entity.Course;
import entity.User;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
import use_case.TaskData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompleteTaskUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private LeaderboardRepository leaderboardRepository;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userRepository = new UserDAO(dbHelper);
        taskRepository = new TaskDAO(dbHelper);
        try {leaderboardRepository = new FileCacheLeaderboardDataAccessObject("test_leaderboards.json");}
        catch (Exception e) {
            System.out.println("Failed to create leaderboard repository: " + e.getMessage());
            fail("Failed to create leaderboard repository: " + e.getMessage());
        }
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

    /**
     * Tests that a newly added task is not completed.
     */
    @Test
    void testAddTaskUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel requestModel = new AddTaskRequestModel("Test Task", "Test Description", startDate, deadline, "Test Course", "testUser");

        addTaskUseCase.execute(requestModel);

        assertNotNull(viewModel.getTasks());
        assertEquals(1, viewModel.getTasks().size());
        assertEquals("Test Task", viewModel.getTasks().get(0).getTitle());
        assertFalse(viewModel.getTasks().get(0).isCompleted());
    }

    /**
     * Tests that a single task can be completed successfully
     */
    @Test
    void testCompleteSingleTask() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel addRequestModel = new AddTaskRequestModel("Test Task", "Test Description", startDate, deadline, "Test Course", "testUser");

        addTaskUseCase.execute(addRequestModel);

        TaskData taskData = viewModel.getTasks().get(0);
        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskData.getId(), "testUser");
        completeTaskUseCase.execute(completeRequestModel);

        assertTrue(viewModel.getTasks().get(0).isCompleted());
    }

    /**
     * Tests that multiple tasks can be completed successfully
     */
    @Test
    void testCompleteMultipleTasks() {
        User user = new User("multiUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);

        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline, "Course 1", "multiUser");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline, "Course 2", "multiUser");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        TaskData taskData1 = viewModel.getTasks().get(0);
        TaskData taskData2 = viewModel.getTasks().get(1);

        CompleteTaskRequestModel completeRequestModel1 = new CompleteTaskRequestModel(taskData1.getId(), "multiUser");
        CompleteTaskRequestModel completeRequestModel2 = new CompleteTaskRequestModel(taskData2.getId(), "multiUser");

        completeTaskUseCase.execute(completeRequestModel1);
        completeTaskUseCase.execute(completeRequestModel2);

        assertTrue(viewModel.getTasks().get(0).isCompleted());
        assertTrue(viewModel.getTasks().get(1).isCompleted());
    }

    /**
     * Tests that a task cannot be wrongly computed by an invalid user.
     */
    @Test
    void testCompleteTaskWithInvalidID() {
        User user = new User("invalidIDUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);

        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(UUID.randomUUID(), "invalidIDUser");

        assertThrows(RuntimeException.class, () -> completeTaskUseCase.execute(completeRequestModel));
    }

    /**
     * Tests that a task can be uncompleted successfully
     */
    @Test
    void testCompleteAndUncompleteTask() {
        User user = new User("complexUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel addRequestModel = new AddTaskRequestModel("Complex Task", "Complex Description", startDate, deadline, "Complex Course", "complexUser");

        addTaskUseCase.execute(addRequestModel);

        TaskData taskData = viewModel.getTasks().get(0);

        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskData.getId(), "complexUser");
        completeTaskUseCase.execute(completeRequestModel);

        assertTrue(viewModel.getTasks().get(0).isCompleted());

        completeTaskUseCase.execute(completeRequestModel);

        assertFalse(viewModel.getTasks().get(0).isCompleted());
    }
}
