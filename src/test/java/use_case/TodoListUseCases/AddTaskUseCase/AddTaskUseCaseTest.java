package use_case.TodoListUseCases.AddTaskUseCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Course;
import entity.Task;
import entity.User;
import data_access.FileCacheUserDataAccessObject;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.UserRepository;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AddTaskUseCaseTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "test_userCache.json";
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
        userRepository = new FileCacheUserDataAccessObject(testFilePath);
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
    void testAddMultipleTasks() {
        User user = new User("multiUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            TodoListViewModel viewModel = new TodoListViewModel();
            TodoListPresenter presenter = new TodoListPresenter(viewModel);
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);

            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime deadline = LocalDateTime.now().plusDays(1);

            AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline, "Course 1", "multiUser");
            AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline, "Course 2", "multiUser");

            addTaskUseCase.execute(requestModel1);
            addTaskUseCase.execute(requestModel2);

            System.out.println("Tasks in ViewModel: " + viewModel.getTasks().size());
            viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle()));
        });
    }

    @Test
    void testAddTaskWithLongDescription() {
        User user = new User("longDescriptionUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            TodoListViewModel viewModel = new TodoListViewModel();
            TodoListPresenter presenter = new TodoListPresenter(viewModel);
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);

            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime deadline = LocalDateTime.now().plusDays(1);
            String longDescription = "This is a very long description".repeat(20);
            AddTaskRequestModel requestModel = new AddTaskRequestModel("Long Description Task", longDescription, startDate, deadline, "Course", "longDescriptionUser");

            addTaskUseCase.execute(requestModel);

            System.out.println("Tasks in ViewModel: " + viewModel.getTasks().size());
            viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle()));
        });
    }

    @Test
    void testAddAndVerifyMultipleTasksWithDifferentUsers() {
        User user1 = new User("user1", "password", new User[]{}, new Course[]{});
        User user2 = new User("user2", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user1);
            fileCacheUserDAO.WriteToCache(user2);

            TodoListViewModel viewModel1 = new TodoListViewModel();
            TodoListPresenter presenter1 = new TodoListPresenter(viewModel1);
            AddTaskUseCase addTaskUseCase1 = new AddTaskUseCase(userRepository, presenter1);

            TodoListViewModel viewModel2 = new TodoListViewModel();
            TodoListPresenter presenter2 = new TodoListPresenter(viewModel2);
            AddTaskUseCase addTaskUseCase2 = new AddTaskUseCase(userRepository, presenter2);

            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime deadline = LocalDateTime.now().plusDays(1);

            AddTaskRequestModel requestModel1 = new AddTaskRequestModel("User1 Task 1", "Description 1", startDate, deadline, "Course 1", "user1");
            AddTaskRequestModel requestModel2 = new AddTaskRequestModel("User1 Task 2", "Description 2", startDate, deadline, "Course 2", "user1");
            AddTaskRequestModel requestModel3 = new AddTaskRequestModel("User2 Task 1", "Description 1", startDate, deadline, "Course 1", "user2");

            addTaskUseCase1.execute(requestModel1);
            addTaskUseCase1.execute(requestModel2);
            addTaskUseCase2.execute(requestModel3);

            System.out.println("User1 Tasks in ViewModel: " + viewModel1.getTasks().size());
            viewModel1.getTasks().forEach(task -> System.out.println("User1 Task Title: " + task.getTitle()));

            System.out.println("User2 Tasks in ViewModel: " + viewModel2.getTasks().size());
            viewModel2.getTasks().forEach(task -> System.out.println("User2 Task Title: " + task.getTitle()));
        });
    }



}
