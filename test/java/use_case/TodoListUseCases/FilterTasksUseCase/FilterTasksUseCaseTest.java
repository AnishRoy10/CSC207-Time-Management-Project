package use_case.TodoListUseCases.FilterTasksUseCase;

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
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskUseCase;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskRequestModel;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksOutputBoundary;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksRequestModel;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksUseCase;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksOutputBoundary;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksRequestModel;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksUseCase;
import use_case.TaskData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FilterTasksUseCaseTest {
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
        try {
            leaderboardRepository = new FileCacheLeaderboardDataAccessObject("test_leaderboards.json");
        } catch (Exception e) {
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

    @Test
    void testFilterTasksUseCase() {
        User user = new User("filterUser", "password", new User[]{}, new Course[]{});
        try {
            userRepository.WriteToCache(user);
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
            fail("Exception thrown while saving user: " + e.getMessage());
        }

        TodoListViewModel viewModel = new TodoListViewModel();
        TodoListPresenter presenter = new TodoListPresenter(viewModel);
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        FilterTasksOutputBoundary filterTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(userRepository, taskRepository, filterTasksOutputBoundary);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline, "Course 1", "filterUser");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline, "Course 2", "filterUser");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        FilterTasksRequestModel filterRequestModel = new FilterTasksRequestModel(true, "filterUser");
        filterTasksUseCase.execute(filterRequestModel);

        System.out.println("Filtered tasks count: " + viewModel.getTasks().size());
        viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle()));
    }


    @Test
    void testFilterAndSortTasksUseCase() {
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
        FilterTasksOutputBoundary filterTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(userRepository, taskRepository, filterTasksOutputBoundary);
        SortTasksOutputBoundary sortTasksOutputBoundary = responseModel -> presenter.present(responseModel);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, sortTasksOutputBoundary);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline1, "Course 1", "complexUser");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline2, "Course 2", "complexUser");

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        // Print task IDs to verify
        System.out.println("Tasks in ViewModel after adding:");
        for (TaskData task : viewModel.getTasks()) {
            System.out.println("Task ID: " + task.getId() + ", Title: " + task.getTitle());
        }

        // Ensure tasks are correctly added and IDs are available
        assertEquals(2, viewModel.getTasks().size());

        TaskData taskData1 = viewModel.getTasks().get(0);

        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskData1.getId(), "complexUser");
        completeTaskUseCase.execute(completeRequestModel);

        // Filter tasks
        FilterTasksRequestModel filterRequestModel = new FilterTasksRequestModel(true, "complexUser");
        filterTasksUseCase.execute(filterRequestModel);

        // Sort tasks
        SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("deadline", true, "complexUser");
        sortTasksUseCase.execute(sortRequestModel);

        System.out.println("Filtered and Sorted tasks count: " + viewModel.getTasks().size());
        viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle() + " Deadline: " + task.getDeadline()));
    }
}
