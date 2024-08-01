package app.gui;

import data_access.SQLDatabaseHelper;
import data_access.TaskDAO;
import data_access.UserDAO;
import data_access.FileCacheLeaderboardDataAccessObject;
import framework.view.TodoListView;
import interface_adapter.controller.TodoListController;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import repositories.LeaderboardRepository;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskUseCase;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksUseCase;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListUseCase;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskUseCase;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksUseCase;

public class TodoListInitializer {
    public static void initializeTodoList(String username) {
        try {
            // Initialize the database helper
            SQLDatabaseHelper dbHelper = new SQLDatabaseHelper();
            dbHelper.initializeDatabase();

            // Initialize the user and task repositories
            UserRepository userRepository = new UserDAO(dbHelper);
            TaskRepository taskRepository = new TaskDAO(dbHelper);

            // Initialize the view model
            TodoListViewModel viewModel = new TodoListViewModel();

            // Initialize the presenter
            TodoListPresenter presenter = new TodoListPresenter(viewModel);

            // Initialize the leaderboard repository (still using file cache for now)
            String leaderboardFilePath = "src/main/java/data_access/leaderboards.json";
            LeaderboardRepository leaderboardRepository = new FileCacheLeaderboardDataAccessObject(leaderboardFilePath);

            // Initialize use cases
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, taskRepository, presenter);
            RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(userRepository, taskRepository, presenter);
            CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, taskRepository, presenter, leaderboardRepository);
            SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, taskRepository, presenter);
            FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(userRepository, taskRepository, presenter);
            LoadTodoListUseCase loadTodoListUseCase = new LoadTodoListUseCase(userRepository, taskRepository, presenter);

            // Initialize the controller
            TodoListController controller = new TodoListController(
                    addTaskUseCase, removeTaskUseCase, completeTaskUseCase, sortTasksUseCase, filterTasksUseCase, loadTodoListUseCase);

            // Initialize and show the view
            TodoListView view = new TodoListView(controller, viewModel, username);
            view.setVisible(true);
        } catch (Exception e) {
            // Print the stack trace and an error message if an exception occurs
            e.printStackTrace();
            System.out.println("Error initializing the to-do list system.");
        }
    }
}
