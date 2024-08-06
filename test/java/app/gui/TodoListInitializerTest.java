//package app.gui;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import interface_adapter.controller.TodoListController;
//import interface_adapter.presenter.TodoListPresenter;
//import interface_adapter.viewmodel.TodoListViewModel;
//import repositories.LeaderboardRepository;
//import repositories.TaskRepository;
//import repositories.UserRepository;
//import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
//import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskUseCase;
//import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksUseCase;
//import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListUseCase;
//import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskUseCase;
//import use_case.TodoListUseCases.SortTasksUseCase.SortTasksUseCase;
//import data_access.SQLDatabaseHelper;
//import framework.view.TodoListView;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class TodoListInitializerTest {
//
//    private SQLDatabaseHelper dbHelper;
//    private UserRepository userRepository;
//    private TaskRepository taskRepository;
//    private LeaderboardRepository leaderboardRepository;
//    private TodoListViewModel viewModel;
//    private TodoListPresenter presenter;
//    private AddTaskUseCase addTaskUseCase;
//    private RemoveTaskUseCase removeTaskUseCase;
//    private CompleteTaskUseCase completeTaskUseCase;
//    private SortTasksUseCase sortTasksUseCase;
//    private FilterTasksUseCase filterTasksUseCase;
//    private LoadTodoListUseCase loadTodoListUseCase;
//    private TodoListController controller;
//
//    @BeforeEach
//    void setUp() {
//        dbHelper = mock(SQLDatabaseHelper.class);
//        userRepository = mock(UserRepository.class);
//        taskRepository = mock(TaskRepository.class);
//        leaderboardRepository = mock(LeaderboardRepository.class);
//        viewModel = new TodoListViewModel();
//        presenter = new TodoListPresenter(viewModel);
//        addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
//        removeTaskUseCase = new RemoveTaskUseCase(userRepository, taskRepository, presenter);
//        completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);
//        sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, presenter);
//        filterTasksUseCase = new FilterTasksUseCase(userRepository, taskRepository, presenter);
//        loadTodoListUseCase = new LoadTodoListUseCase(userRepository, taskRepository, presenter);
//        controller = new TodoListController(loadTodoListUseCase);
//    }
//
//    @Test
//    void testInitializeUserTodoList() {
//        String username = "testUser";
//
//        // Mocking the required objects and methods
//        doNothing().when(dbHelper).initializeDatabase();
//        when(controller.loadTodoList(username)).thenReturn(new TodoListView(controller, viewModel, username));
//
//        // Calling the method to initialize the user's to-do list
//        TodoListInitializer.initializeTodoList(username);
//
//        // Verifying the method calls
//        verify(dbHelper, times(1)).initializeDatabase();
//
//        // Checking if the to-do list view is initialized correctly
//        assertNotNull(controller);
//    }
//
//    @Test
//    void testInitializeCourseTodoList() {
//        String username = "testUser";
//        String courseName = "testCourse";
//
//        // Mocking the required objects and methods
//        doNothing().when(dbHelper).initializeDatabase();
//        when(controller.loadTodoList(username, courseName)).thenReturn(new TodoListView(controller, viewModel, username, courseName));
//
//        // Calling the method to initialize the course-specific to-do list
//        TodoListInitializer.initializeTodoList(username, courseName);
//
//        // Verifying the method calls
//        verify(dbHelper, times(1)).initializeDatabase();
//
//        // Checking if the to-do list view is initialized correctly
//        assertNotNull(controller);
//    }
//}