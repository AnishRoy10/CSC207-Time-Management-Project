package use_case.TodoListUseCases.RemoveTaskUseCase;

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
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RemoveTaskUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() throws IOException {
        dbHelper = new SQLDatabaseHelper();
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
            e.printStackTrace();
        }
    }

    @Test
    void testRemoveTaskUseCase() {
        User user = new User("removeUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(userRepository, taskRepository, presenter);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel addRequestModel = new AddTaskRequestModel("Task to Remove", "Description", startDate, deadline, "Course", "removeUser");

        addTaskUseCase.execute(addRequestModel);

        assertEquals(1, viewModel.getTasks().size());

        UUID taskId = viewModel.getTasks().get(0).getId();
        RemoveTaskRequestModel removeRequestModel = new RemoveTaskRequestModel(taskId, "removeUser");
        removeTaskUseCase.execute(removeRequestModel);

        assertEquals(0, viewModel.getTasks().size());
    }

    @Test
    void testRemoveNonExistingTask() {
        User user = new User("removeUser2", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(userRepository, taskRepository, presenter);

        RemoveTaskRequestModel removeRequestModel = new RemoveTaskRequestModel(UUID.randomUUID(), "removeUser2");
        Exception exception = assertThrows(RuntimeException.class, () -> removeTaskUseCase.execute(removeRequestModel));

        assertTrue(exception.getMessage().contains("Task not found"));
    }

    @Test
    void testRemoveTaskMultipleUsers() {
        User user1 = new User("user1", "password", new User[]{}, new Course[]{});
        User user2 = new User("user2", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user1);
            userRepository.WriteToCache(user2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TodoListViewModel viewModel1 = new TodoListViewModel();
        TodoListPresenter presenter1 = new TodoListPresenter(viewModel1);
        TodoListViewModel viewModel2 = new TodoListViewModel();
        TodoListPresenter presenter2 = new TodoListPresenter(viewModel2);

        AddTaskUseCase addTaskUseCase1 = new AddTaskUseCase(userRepository, taskRepository, presenter1);
        AddTaskUseCase addTaskUseCase2 = new AddTaskUseCase(userRepository, taskRepository, presenter2);
        RemoveTaskUseCase removeTaskUseCase1 = new RemoveTaskUseCase(userRepository, taskRepository, presenter1);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel addRequestModel1 = new AddTaskRequestModel("Task for User1", "Description", startDate, deadline, "Course", "user1");
        AddTaskRequestModel addRequestModel2 = new AddTaskRequestModel("Task for User2", "Description", startDate, deadline, "Course", "user2");

        addTaskUseCase1.execute(addRequestModel1);
        addTaskUseCase2.execute(addRequestModel2);

        assertEquals(1, viewModel1.getTasks().size());
        assertEquals(1, viewModel2.getTasks().size());

        UUID taskIdUser1 = viewModel1.getTasks().get(0).getId();
        RemoveTaskRequestModel removeRequestModelUser1 = new RemoveTaskRequestModel(taskIdUser1, "user1");
        removeTaskUseCase1.execute(removeRequestModelUser1);

        assertEquals(0, viewModel1.getTasks().size());
        assertEquals(1, viewModel2.getTasks().size()); // Ensure tasks of user2 are not affected
    }

    @Test
    void testRemoveTaskPreservesOtherTasks() {
        User user = new User("removeUser3", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(userRepository, taskRepository, presenter);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
        AddTaskRequestModel addRequestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline1, "Course", "removeUser3");
        AddTaskRequestModel addRequestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline2, "Course", "removeUser3");

        addTaskUseCase.execute(addRequestModel1);
        addTaskUseCase.execute(addRequestModel2);

        assertEquals(2, viewModel.getTasks().size());

        UUID taskIdToRemove = viewModel.getTasks().get(0).getId();
        RemoveTaskRequestModel removeRequestModel = new RemoveTaskRequestModel(taskIdToRemove, "removeUser3");
        removeTaskUseCase.execute(removeRequestModel);

        assertEquals(1, viewModel.getTasks().size());
        assertEquals("Task 2", viewModel.getTasks().get(0).getTitle());
    }

    @Test
    void testRemoveTaskFromEmptyTodoList() {
        User user = new User("removeUser4", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(userRepository, taskRepository, presenter);

        RemoveTaskRequestModel removeRequestModel = new RemoveTaskRequestModel(UUID.randomUUID(), "removeUser4");
        Exception exception = assertThrows(RuntimeException.class, () -> removeTaskUseCase.execute(removeRequestModel));

        assertTrue(exception.getMessage().contains("Task not found"));
    }
}
