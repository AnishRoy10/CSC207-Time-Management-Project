package app.gui;

import data_access.FileCacheUserDataAccessObject;
import framework.view.TodoListView;
import interface_adapter.controller.TodoListController;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
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
            // Initialize the user repository with the file path
            String filePath = "src/main/java/data_access/userCache.json";
            UserRepository userRepository = new FileCacheUserDataAccessObject(filePath);

            // Initialize the view model
            TodoListViewModel viewModel = new TodoListViewModel();

            // Initialize the presenter
            TodoListPresenter presenter = new TodoListPresenter(viewModel);

            // Initialize use cases
            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);
            RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(userRepository, presenter);
            CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, presenter);
            SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, presenter);
            FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(userRepository, presenter);
            LoadTodoListUseCase loadTodoListUseCase = new LoadTodoListUseCase(userRepository, presenter);

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
