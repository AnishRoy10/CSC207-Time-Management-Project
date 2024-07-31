package use_case.TodoListUseCases.CompleteTaskUseCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data_access.FileCacheLeaderboardDataAccessObject;
import entity.Course;
import entity.Task;
import entity.User;
import data_access.FileCacheUserDataAccessObject;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
import use_case.TaskData;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompleteTaskUseCaseTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "test_userCache.json";
    private UserRepository userRepository;
    private LeaderboardRepository leaderboardRepository;
    private final String testLeaderboardFilePath = "test_leaderboards.json";

    @BeforeEach
    void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
        userRepository = new FileCacheUserDataAccessObject(testFilePath);
        leaderboardRepository = new FileCacheLeaderboardDataAccessObject(testLeaderboardFilePath);
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddTaskUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            TodoListViewModel viewModel = new TodoListViewModel();
            TodoListPresenter presenter = new TodoListPresenter(viewModel);
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);

            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime deadline = LocalDateTime.now().plusDays(1);
            AddTaskRequestModel requestModel = new AddTaskRequestModel("Test Task", "Test Description", startDate, deadline, "Test Course", "testUser");

            addTaskUseCase.execute(requestModel);

            System.out.println("Tasks in ViewModel: " + viewModel.getTasks().size());
            viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle()));
        });
    }

    @Test
    void testCompleteSingleTask() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            TodoListViewModel viewModel = new TodoListViewModel();
            TodoListPresenter presenter = new TodoListPresenter(viewModel);
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);
            CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, presenter, leaderboardRepository);

            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime deadline = LocalDateTime.now().plusDays(1);
            AddTaskRequestModel addRequestModel = new AddTaskRequestModel("Test Task", "Test Description", startDate, deadline, "Test Course", "testUser");

            addTaskUseCase.execute(addRequestModel);

            TaskData taskData = viewModel.getTasks().get(0);
            CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskData.getId(), "testUser");
            completeTaskUseCase.execute(completeRequestModel);

            System.out.println("Task completion status: " + taskData.isCompleted());
        });
    }

    @Test
    void testCompleteMultipleTasks() {
        User user = new User("multiUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            TodoListViewModel viewModel = new TodoListViewModel();
            TodoListPresenter presenter = new TodoListPresenter(viewModel);
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);
            CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, presenter, leaderboardRepository);

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

            System.out.println("Task 1 completion status: " + taskData1.isCompleted());
            System.out.println("Task 2 completion status: " + taskData2.isCompleted());
        });
    }

    @Test
    void testCompleteTaskWithInvalidID() {
        User user = new User("invalidIDUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            TodoListViewModel viewModel = new TodoListViewModel();
            TodoListPresenter presenter = new TodoListPresenter(viewModel);
            CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, presenter, leaderboardRepository);

            CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(UUID.randomUUID(), "invalidIDUser");

            assertThrows(RuntimeException.class, () -> completeTaskUseCase.execute(completeRequestModel));
        });
    }

    @Test
    void testCompleteAndRecompleteTask() {
        User user = new User("complexUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            TodoListViewModel viewModel = new TodoListViewModel();
            TodoListPresenter presenter = new TodoListPresenter(viewModel);
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);
            CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, presenter, leaderboardRepository);

            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime deadline = LocalDateTime.now().plusDays(1);
            AddTaskRequestModel addRequestModel = new AddTaskRequestModel("Complex Task", "Complex Description", startDate, deadline, "Complex Course", "complexUser");

            addTaskUseCase.execute(addRequestModel);

            TaskData taskData = viewModel.getTasks().get(0);

            CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskData.getId(), "complexUser");
            completeTaskUseCase.execute(completeRequestModel);

            System.out.println("Task completion status after first completion: " + taskData.isCompleted());

            completeTaskUseCase.execute(completeRequestModel);

            System.out.println("Task completion status after second completion: " + taskData.isCompleted());
        });
    }

}
