package use_case.TodoListUseCases.SortTasksUseCase;

import data_access.SQLLeaderboardDAO;
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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SortTasksUseCaseTest {
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

        System.out.println("Sorted tasks count: " + viewModel.getTasks().size());
        viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle() + " Deadline: " + task.getDeadline()));
    }
}
