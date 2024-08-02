package use_case.TodoListUseCases.AddTaskUseCase;

import data_access.SQLDatabaseHelper;
import data_access.TaskDAO;
import data_access.UserDAO;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.core.DB;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
import entity.Course;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AddTaskUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userRepository = new UserDAO(dbHelper);
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
    void testAddTaskUseCase() {
        // Create and save the user
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
    }

    @Test
    void testAddMultipleTasks() {
        // Create and save the user
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

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);

        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline, "Course 1", "multiUser");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline, "Course 2", "multiUser");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        assertNotNull(viewModel.getTasks());
        assertEquals(2, viewModel.getTasks().size());
        assertEquals("Task 1", viewModel.getTasks().get(0).getTitle());
        assertEquals("Task 2", viewModel.getTasks().get(1).getTitle());
    }

    @Test
    void testAddTaskWithLongDescription() {
        // Create and save the user
        User user = new User("longDescriptionUser", "password", new User[]{}, new Course[]{});
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
        String longDescription = "This is a very long description".repeat(20);
        AddTaskRequestModel requestModel = new AddTaskRequestModel("Long Description Task", longDescription, startDate, deadline, "Course", "longDescriptionUser");

        addTaskUseCase.execute(requestModel);

        assertNotNull(viewModel.getTasks());
        assertEquals(1, viewModel.getTasks().size());
        assertEquals("Long Description Task", viewModel.getTasks().get(0).getTitle());
    }

    @Test
    void testAddAndVerifyMultipleTasksWithDifferentUsers() {
        // Create and save the users
        User user1 = new User("user1", "password", new User[]{}, new Course[]{});
        User user2 = new User("user2", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user1);
            userRepository.WriteToCache(user2);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel1 = new TodoListViewModel();
        TodoListPresenter presenter1 = new TodoListPresenter(viewModel1);
        AddTaskUseCase addTaskUseCase1 = new AddTaskUseCase(userRepository, taskRepository, presenter1);

        TodoListViewModel viewModel2 = new TodoListViewModel();
        TodoListPresenter presenter2 = new TodoListPresenter(viewModel2);
        AddTaskUseCase addTaskUseCase2 = new AddTaskUseCase(userRepository, taskRepository, presenter2);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);

        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("User1 Task 1", "Description 1", startDate, deadline, "Course 1", "user1");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("User1 Task 2", "Description 2", startDate, deadline, "Course 2", "user1");
        AddTaskRequestModel requestModel3 = new AddTaskRequestModel("User2 Task 1", "Description 1", startDate, deadline, "Course 1", "user2");

        addTaskUseCase1.execute(requestModel1);
        addTaskUseCase1.execute(requestModel2);
        addTaskUseCase2.execute(requestModel3);

        assertNotNull(viewModel1.getTasks());
        assertEquals(2, viewModel1.getTasks().size());
        assertEquals("User1 Task 1", viewModel1.getTasks().get(0).getTitle());
        assertEquals("User1 Task 2", viewModel1.getTasks().get(1).getTitle());

        assertNotNull(viewModel2.getTasks());
        assertEquals(1, viewModel2.getTasks().size());
        assertEquals("User2 Task 1", viewModel2.getTasks().get(0).getTitle());
    }
}
