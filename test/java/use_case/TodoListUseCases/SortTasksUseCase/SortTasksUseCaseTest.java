package use_case.TodoListUseCases.SortTasksUseCase;

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
import use_case.TodoListUseCases.AddTaskUseCase.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortTasksUseCaseTest {
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
    void testSortTasksUseCase() {
        User user = new User("sortUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        SortTasksOutputBoundary sortTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, sortTasksOutputBoundary);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline1, "Course 1", "sortUser");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline2, "Course 2", "sortUser");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("deadline", true, "sortUser");
        sortTasksUseCase.execute(sortRequestModel);

        List<TaskData> sortedTasks = viewModel.getTasks();
        assertNotNull(sortedTasks);
        assertEquals(2, sortedTasks.size());
        assertEquals("Task 1", sortedTasks.get(0).getTitle());
        assertEquals("Task 2", sortedTasks.get(1).getTitle());
    }

    @Test
    void testSortTasksUserNotFound() {
        SortTasksOutputBoundary mockOutputBoundary = responseModel -> {
            // No-op
        };

        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, mockOutputBoundary);

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("deadline", true, "nonExistentUser");

        Exception exception = assertThrows(RuntimeException.class, () -> sortTasksUseCase.execute(sortRequestModel));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testSortTasksByTitle() {
        User user = new User("sortUserByTitle", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        SortTasksOutputBoundary sortTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, sortTasksOutputBoundary);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("A Task", "Description 1", startDate, deadline, "Course 1", "sortUserByTitle");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("B Task", "Description 2", startDate, deadline, "Course 2", "sortUserByTitle");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("title", true, "sortUserByTitle");
        sortTasksUseCase.execute(sortRequestModel);

        List<TaskData> sortedTasks = viewModel.getTasks();
        assertNotNull(sortedTasks);
        assertEquals(2, sortedTasks.size());
        assertEquals("A Task", sortedTasks.get(0).getTitle());
        assertEquals("B Task", sortedTasks.get(1).getTitle());
    }

    @Test
    void testSortTasksDescending() {
        User user = new User("sortUserDesc", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        SortTasksOutputBoundary sortTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, sortTasksOutputBoundary);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("A Task", "Description 1", startDate, deadline, "Course 1", "sortUserDesc");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("B Task", "Description 2", startDate, deadline, "Course 2", "sortUserDesc");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("title", false, "sortUserDesc");
        sortTasksUseCase.execute(sortRequestModel);

        List<TaskData> sortedTasks = viewModel.getTasks();
        assertNotNull(sortedTasks);
        assertEquals(2, sortedTasks.size());
        assertEquals("B Task", sortedTasks.get(0).getTitle());
        assertEquals("A Task", sortedTasks.get(1).getTitle());
    }

    @Test
    void testInvalidSortingCriteria() {
        User user = new User("sortUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SortTasksOutputBoundary mockOutputBoundary = responseModel -> {
            // No-op
        };

        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, mockOutputBoundary);

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("invalidCriteria", true, "sortUser");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> sortTasksUseCase.execute(sortRequestModel));
        assertEquals("Unknown sorting criteria: invalidCriteria", exception.getMessage());
    }


    @Test
    void testSortTasksByCourse() {
        User user = new User("sortUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        SortTasksOutputBoundary sortTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, sortTasksOutputBoundary);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline1, "Course B", "sortUser");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline2, "Course A", "sortUser");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("course", true, "sortUser");
        sortTasksUseCase.execute(sortRequestModel);

        List<TaskData> tasks = viewModel.getTasks();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals("Course A", tasks.get(0).getCourse());
        assertEquals("Course B", tasks.get(1).getCourse());
    }

    @Test
    void testSortTasksByCompletion() {
        User user = new User("sortUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        SortTasksOutputBoundary sortTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, sortTasksOutputBoundary);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline1, "Course", "sortUser");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline2, "Course", "sortUser");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        // Mark the first task as completed
        List<Task> tasksBeforeCompletion = taskRepository.getAllTasks(user.getUsername());
        Task task1 = tasksBeforeCompletion.stream().filter(task -> task.getTitle().equals("Task 1")).findFirst().orElse(null);
        if (task1 != null) {
            task1.completeTask();
            taskRepository.WriteToCache(task1, user.getUsername());
        }

        // Verify task completion status update
        Task updatedTask1 = taskRepository.ReadFromCache(task1.getId());
        System.out.println("Updated Task 1 Completion Status: " + updatedTask1.isCompleted());

        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("completion", true, "sortUser");
        sortTasksUseCase.execute(sortRequestModel);

        List<TaskData> tasks = viewModel.getTasks();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());

        // Debugging statements
        System.out.println("Sorted Tasks:");
        tasks.forEach(task -> System.out.println("Task: " + task.getTitle() + " | Completed: " + task.isCompleted()));

        // Verify sorting by completion status
        assertTrue(tasks.get(0).isCompleted(), "Expected first task to be completed");
        assertFalse(tasks.get(1).isCompleted(), "Expected second task to be incomplete");
    }

    @Test
    void testSortTasksRequestModelGettersAndSetters() {
        String criteria = "deadline";
        boolean ascending = true;
        String username = "testUser";

        SortTasksRequestModel requestModel = new SortTasksRequestModel(criteria, ascending, username);

        assertEquals(criteria, requestModel.getCriteria());
        assertEquals(ascending, requestModel.isAscending());
        assertEquals(username, requestModel.getUsername());

        String newCriteria = "title";
        boolean newAscending = false;
        String newUsername = "newUser";

        requestModel.setCriteria(newCriteria);
        requestModel.setAscending(newAscending);
        requestModel.setUsername(newUsername);

        assertEquals(newCriteria, requestModel.getCriteria());
        assertEquals(newAscending, requestModel.isAscending());
        assertEquals(newUsername, requestModel.getUsername());
    }

    @Test
    void testSortTasksResponseModelGettersAndSetters() {
        List<TaskData> tasks = new ArrayList<>();
        SortTasksResponseModel responseModel = new SortTasksResponseModel(tasks);

        assertEquals(tasks, responseModel.getTasks());

        List<TaskData> newTasks = new ArrayList<>();
        responseModel.setTasks(newTasks);

        assertEquals(newTasks, responseModel.getTasks());
        assertNotNull(responseModel.getTasks());
    }
}
