package app.gui;

import interface_adapter.controller.TodoListController;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.TodoListViewModel;
import data_access.TodoListDataAccessObject;
import repositories.TodoListRepository;
import use_case.*;
import framework.view.TodoListView;

public class ApplicationInitializer {
    public static void main(String[] args) {
        // Initialize repository
        TodoListRepository repository = new TodoListDataAccessObject();

        // Initialize view model
        TodoListViewModel viewModel = new TodoListViewModel();

        // Initialize presenter
        TodoListPresenter presenter = new TodoListPresenter(viewModel);

        // Initialize use cases
        AddTaskInputBoundary addTaskUseCase = new AddTaskUseCase(repository, presenter);
        RemoveTaskInputBoundary removeTaskUseCase = new RemoveTaskUseCase(repository, presenter);
        CompleteTaskInputBoundary completeTaskUseCase = new CompleteTaskUseCase(repository, presenter);
        LoadTodoListInputBoundary loadTodoListUseCase = new LoadTodoListUseCase(repository, presenter);
        SortTasksInputBoundary sortTasksUseCase = new SortTasksUseCase(repository, presenter);
        FilterTasksInputBoundary filterTasksUseCase = new FilterTasksUseCase(repository, presenter);

        // Initialize controller
        TodoListController controller = new TodoListController(
                addTaskUseCase,
                removeTaskUseCase,
                completeTaskUseCase,
                sortTasksUseCase,
                filterTasksUseCase,
                loadTodoListUseCase
        );

        // Initialize and display view
        TodoListView view = new TodoListView(controller, viewModel);
        view.setVisible(true);
    }
}
