package use_case.TodoListUseCases.LoadTodoListUseCase;

import data_access.SQLDatabaseHelper;
import data_access.TaskDAO;
import data_access.UserDAO;
import entity.Course;
import entity.Task;
import entity.User;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TaskData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoadTodoListUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private TodoListPresenter todoListPresenter;
    private LoadTodoListUseCase loadTodoListUseCase;
    private TodoListViewModel viewModel;

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:Saves/TestDB.db");
        dbHelper.initializeDatabase();
        userRepository = new UserDAO(dbHelper);
        taskRepository = new TaskDAO(dbHelper);
        viewModel = new TodoListViewModel();
        todoListPresenter = new TodoListPresenter(viewModel);
        loadTodoListUseCase = new LoadTodoListUseCase(userRepository, taskRepository, todoListPresenter);
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
    void testLoadTodoListSuccessfully() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        Task task1 = new Task(user.getUsername(), "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        Task task2 = new Task(user.getUsername(), "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");

        user.getTodoList().addTask(task1);
        user.getTodoList().addTask(task2);

        try {userRepository.WriteToCache(user);}
        catch (Exception e) {e.printStackTrace();}
        taskRepository.WriteToCache(task1, user.getUsername());
        taskRepository.WriteToCache(task2, user.getUsername());

        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel(user.getUsername());
        loadTodoListUseCase.execute(requestModel);

        List<TaskData> tasks = viewModel.getTasks();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Task 1")));
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Task 2")));
    }

    @Test
    void testLoadTodoListUserNotFound() {
        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel("nonExistentUser");
        Exception exception = assertThrows(RuntimeException.class, () -> loadTodoListUseCase.execute(requestModel));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testLoadTodoListEmpty() {
        User user = new User("emptyUser", "password", new User[]{}, new Course[]{});
        try {userRepository.WriteToCache(user);}
        catch (Exception e) {e.printStackTrace();}

        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel(user.getUsername());
        loadTodoListUseCase.execute(requestModel);

        List<TaskData> tasks = viewModel.getTasks();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }


    @Test
    void testLoadTodoListIOException() {
        UserRepository mockUserRepository = new UserDAO(dbHelper) {
            @Override
            public User findByUsername(String username) throws IOException {
                throw new IOException("Database read error");
            }
        };
        LoadTodoListUseCase loadTodoListUseCaseWithMock = new LoadTodoListUseCase(mockUserRepository, taskRepository, todoListPresenter);

        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel("filterUser");

        Exception exception = assertThrows(RuntimeException.class, () -> loadTodoListUseCaseWithMock.execute(requestModel));
        assertEquals("Database error", exception.getMessage());
        assertEquals("Database read error", exception.getCause().getMessage());
    }

    @Test
    void testSetUsername() {
        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel("initialUsername");
        requestModel.setUsername("newUsername");
        assertEquals("newUsername", requestModel.getUsername());
    }

    @Test
    void testLoadTodoListWithCourseName() {
        User user = new User("testUserWithCourse", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        Task task1 = new Task(user.getUsername(), "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        taskRepository.WriteToCache(task1, user.getUsername(), "Course 1");

        Task task2 = new Task(user.getUsername(), "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");
        taskRepository.WriteToCache(task2, user.getUsername(), "Course 2");

        // Testing the constructor with courseName
        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel(user.getUsername(), "Course 1");
        loadTodoListUseCase.execute(requestModel);

        List<TaskData> tasks = viewModel.getTasks();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());

        // Testing the setCourseName method
        requestModel.setCourseName("Course 2");
        loadTodoListUseCase.execute(requestModel);

        tasks = viewModel.getTasks();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Task 2", tasks.get(0).getTitle());
    }

}
