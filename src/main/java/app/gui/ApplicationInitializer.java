package app.gui;

import interface_adapter.controller.TodoListController;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.TodoListViewModel;
import data_access.TodoListDataAccessObject;
import repositories.TodoListRepository;
import framework.view.TodoListView;
import use_case.AddTaskUseCase.AddTaskUseCase;
import use_case.CompleteTaskUseCase.CompleteTaskUseCase;
import use_case.FilterTasksUseCase.FilterTasksUseCase;
import use_case.LoadTodoListUseCase.LoadTodoListUseCase;
import use_case.RemoveTaskUseCase.RemoveTaskUseCase;
import use_case.SortTasksUseCase.SortTasksUseCase;

public class ApplicationInitializer {
    public static void main(String[] args) {
        // Initialize the repository
        TodoListRepository repository = new TodoListDataAccessObject();

        // Initialize the view model
        TodoListViewModel viewModel = new TodoListViewModel();

        // Initialize the presenter
        TodoListPresenter presenter = new TodoListPresenter(viewModel);

        // Initialize use cases
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(repository, presenter);
        RemoveTaskUseCase removeTaskUseCase = new RemoveTaskUseCase(repository, presenter);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(repository, presenter);
        SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(repository, presenter);
        FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(repository, presenter);
        LoadTodoListUseCase loadTodoListUseCase = new LoadTodoListUseCase(repository, presenter);

        // Initialize the controller
        TodoListController controller = new TodoListController(
                addTaskUseCase, removeTaskUseCase, completeTaskUseCase, sortTasksUseCase, filterTasksUseCase, loadTodoListUseCase);

        // Initialize and show the view
        TodoListView view = new TodoListView(controller, viewModel);
        view.setVisible(true);
    }
}
