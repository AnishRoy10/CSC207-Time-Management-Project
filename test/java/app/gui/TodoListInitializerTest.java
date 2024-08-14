package app.gui;

import data_access.SQLDatabaseHelper;
import data_access.TaskDAO;
import data_access.UserDAO;
import framework.view.TodoListView;
import interface_adapter.controller.TodoListController;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskUseCase;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksUseCase;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListUseCase;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskUseCase;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksUseCase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class TodoListInitializerTest {

    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private LeaderboardRepository leaderboardRepository;

    @BeforeEach
    public void setUp() {
        // Mock the dependencies
        dbHelper = mock(SQLDatabaseHelper.class);
        userRepository = mock(UserDAO.class);
        taskRepository = mock(TaskDAO.class);
        leaderboardRepository = mock(LeaderboardRepository.class);

        // Ensure the database initializes without throwing an exception
        doNothing().when(dbHelper).initializeDatabase();
    }

    @Test
    public void testInitializeTodoListWithoutCourse() {
        // Test initializing without a course (personal to-do list)
        assertDoesNotThrow(() -> TodoListInitializer.initializeTodoList("testUser"));
    }

    @Test
    public void testInitializeTodoListWithCourse() {
        // Test initializing with a course
        assertDoesNotThrow(() -> TodoListInitializer.initializeTodoList("testUser", "testCourse"));
    }

    @Test
    public void testInitializeTodoListWithMocks() {
        // Initialize the view model
        TodoListViewModel viewModel = new TodoListViewModel();

        // Initialize the presenter
        TodoListPresenter presenter = new TodoListPresenter(viewModel);

        // Initialize use cases
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
        RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(userRepository, taskRepository, presenter);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, presenter);
        FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(userRepository, taskRepository, presenter);
        LoadTodoListUseCase loadTodoListUseCase = new LoadTodoListUseCase(userRepository, taskRepository, presenter);

        // Mock the controller and view
        TodoListController controller = new TodoListController(
                addTaskUseCase, removeTaskUseCase, completeTaskUseCase, sortTasksUseCase, filterTasksUseCase, loadTodoListUseCase);
        TodoListView view = mock(TodoListView.class);

        // Ensure the view sets visibility without throwing an exception
        doNothing().when(view).setVisible(true);

        // Test initializing with mocks
        assertDoesNotThrow(() -> {
            TodoListInitializer.initializeTodoList("testUser", "testCourse");
            verify(view, never()).setVisible(true); // view is not actually shown here since we mock it
        });
    }
}
