package use_case.TodoListUseCases.CompleteTaskUseCase;

import data_access.SQLLeaderboardDAO;
import data_access.SQLDatabaseHelper;
import data_access.TaskDAO;
import data_access.UserDAO;
import entity.Course;
import entity.User;
import entity.Task;
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
import use_case.TodoListUseCases.TaskData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.UUID;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


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
        leaderboardRepository = new SQLLeaderboardDAO(dbHelper);
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

    @Test
    void testCompleteTaskWithNonExistentUser() {
        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);

        // Creating a CompleteTaskRequestModel with a non-existent user
        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(UUID.randomUUID(), "nonExistentUser");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            completeTaskUseCase.execute(completeRequestModel);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testCompleteTaskIOException() throws IOException {
        UserRepository mockUserRepository = mock(UserRepository.class);
        TaskRepository mockTaskRepository = mock(TaskRepository.class);
        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        LeaderboardRepository mockLeaderboardRepository = mock(LeaderboardRepository.class);

        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(mockUserRepository, mockTaskRepository, presenter, mockLeaderboardRepository);

        UUID taskId = UUID.randomUUID();

        try {
            when(mockUserRepository.findByUsername("testUser")).thenThrow(new IOException("Database error"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskId, "testUser");

        // Since the IOException is caught inside execute, it won't be thrown here
        completeTaskUseCase.execute(completeRequestModel);

        // Check if any further operations were stopped due to the exception
        verify(mockTaskRepository, never()).ReadFromCache(any(UUID.class));
        verify(mockTaskRepository, never()).WriteToCache(any(Task.class), any(String.class));
        verify(mockLeaderboardRepository, never()).writeToCache(anyMap());
    }

    @Test
    void testAddTaskRequestModelSetters() {
        AddTaskRequestModel requestModel = new AddTaskRequestModel("Initial Title", "Initial Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Initial Course", "InitialUser");

        requestModel.setTitle("Updated Title");
        assertEquals("Updated Title", requestModel.getTitle());

        requestModel.setDescription("Updated Description");
        assertEquals("Updated Description", requestModel.getDescription());

        LocalDateTime newStartDate = LocalDateTime.now().minusDays(1);
        requestModel.setStartDate(newStartDate);
        assertEquals(newStartDate, requestModel.getStartDate());

        LocalDateTime newDeadline = LocalDateTime.now().plusDays(2);
        requestModel.setDeadline(newDeadline);
        assertEquals(newDeadline, requestModel.getDeadline());

        requestModel.setCourse("Updated Course");
        assertEquals("Updated Course", requestModel.getCourse());

        requestModel.setUsername("UpdatedUser");
        assertEquals("UpdatedUser", requestModel.getUsername());
    }

    @Test
    public void testCompleteTaskResponseModelSettersAndConstructors() {
        UUID taskId = UUID.randomUUID();
        TaskData taskData = new TaskData(taskId, "user1", "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course 1", null);

        CompleteTaskResponseModel responseModel = new CompleteTaskResponseModel(taskData);

        assertEquals(taskData, responseModel.getTaskData());

        responseModel.setTaskId(taskId);
        assertEquals(taskId, responseModel.getTaskId());

        CompleteTaskResponseModel responseModelWithId = new CompleteTaskResponseModel(taskData, taskId);
        assertEquals(taskData, responseModelWithId.getTaskData());
        assertEquals(taskId, responseModelWithId.getTaskId());
    }

    @Test
    public void testCompleteTaskRequestModelSetters() {
        UUID taskId = UUID.randomUUID();
        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(taskId, "initialUser");

        // Testing setters
        UUID newTaskId = UUID.randomUUID();
        requestModel.setTaskId(newTaskId);
        assertEquals(newTaskId, requestModel.getTaskId());

        requestModel.setUsername("updatedUser");
        assertEquals("updatedUser", requestModel.getUsername());
    }

    @Test
    void testCompleteTaskRequestModelWithCourseName() {
        User user = new User("testUserWithCourse", "password", new User[]{}, new Course[]{});
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
        AddTaskRequestModel addRequestModel = new AddTaskRequestModel("Test Task", "Test Description", startDate, deadline, "Test Course", "testUserWithCourse", "Course 1");

        addTaskUseCase.execute(addRequestModel);

        TaskData taskData = viewModel.getTasks().get(0);

        // Testing the constructor with courseName
        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskData.getId(), "testUserWithCourse", "Course 1");
        completeTaskUseCase.execute(completeRequestModel);

        assertTrue(viewModel.getTasks().get(0).isCompleted());

        // Testing the setter for courseName
        completeRequestModel.setCourseName("Course 2");
        assertEquals("Course 2", completeRequestModel.getCourseName());
    }

}
