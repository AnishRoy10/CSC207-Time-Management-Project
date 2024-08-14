package app.gui;

import data_access.*;
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

/**
 * The {@code TodoListInitializer} class is responsible for initializing and configuring
 * the to-do list system, including the database, repositories, use cases, controller, and view.
 * This class provides methods to initialize the to-do list either for a personal user or a course-specific list.
 */
public class TodoListInitializer {

    /**
     * Initializes the to-do list system for a personal user.
     *
     * @param username the username of the user for whom the to-do list is being initialized.
     */
    public static void initializeTodoList(String username) {
        initializeTodoList(username, null);
    }

    /**
     * Initializes the to-do list system for either a personal user or a course-specific list.
     * This method sets up the necessary components, including the database helper, repositories,
     * use cases, controller, and view, and makes the view visible.
     *
     * @param username   the username of the user for whom the to-do list is being initialized.
     * @param courseName the name of the course, if applicable, for which the to-do list is being initialized;
     *                   if {@code null}, the to-do list will be for the user's personal tasks.
     */
    public static void initializeTodoList(String username, String courseName) {
        try {
            // Initialize the database helper
            SQLDatabaseHelper dbHelper = new SQLDatabaseHelper();
            dbHelper.initializeDatabase();

            // Initialize the user and task repositories
            UserRepository userRepository = new UserDAO(dbHelper);
            TaskRepository taskRepository = new TaskDAO(dbHelper);
            LeaderboardRepository leaderboardRepository = new SQLLeaderboardDAO(dbHelper);

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

            // Initialize the controller
            TodoListController controller = new TodoListController(
                    addTaskUseCase, removeTaskUseCase, completeTaskUseCase, sortTasksUseCase, filterTasksUseCase, loadTodoListUseCase);

            // Initialize and show the view
            TodoListView view = new TodoListView(controller, viewModel, username, courseName);
            view.setVisible(true);
        } catch (Exception e) {
            // Print the stack trace and an error message if an exception occurs
            e.printStackTrace();
            System.out.println("Error initializing the to-do list system.");
        }
    }
}
