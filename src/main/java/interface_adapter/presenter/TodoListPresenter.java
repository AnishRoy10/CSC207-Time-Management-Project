package interface_adapter.presenter;

import interface_adapter.viewmodel.TodoListViewModel;
import use_case.TodoListUseCases.TaskData;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskOutputBoundary;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskResponseModel;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskOutputBoundary;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskResponseModel;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksOutputBoundary;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksResponseModel;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListOutputBoundary;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListResponseModel;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskOutputBoundary;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskResponseModel;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksOutputBoundary;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksResponseModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The TodoListPresenter class acts as the bridge between the application's use cases and the view model.
 * It implements various output boundaries to handle the presentation logic for adding, removing,
 * completing, sorting, loading, and filtering tasks in the to-do list.
 */
public class TodoListPresenter implements AddTaskOutputBoundary, RemoveTaskOutputBoundary,
        CompleteTaskOutputBoundary, SortTasksOutputBoundary,
        LoadTodoListOutputBoundary, FilterTasksOutputBoundary {

    private final TodoListViewModel viewModel;

    /**
     * Constructs a TodoListPresenter with the specified view model.
     *
     * @param viewModel The view model that this presenter will update with task data.
     */
    public TodoListPresenter(TodoListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Presents the response model after adding a task by updating the view model with the new list of tasks.
     *
     * @param responseModel The response model containing the updated list of tasks.
     */
    @Override
    public void present(AddTaskResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task added: " + task.getTitle()));
    }

    /**
     * Presents an error message when adding a task fails due to missing required fields.
     *
     * @param errorMessage The error message to be displayed to the user.
     */
    @Override
    public void presentError(String errorMessage) {
        viewModel.setErrorMessage(errorMessage); // Pass the error message to the ViewModel
    }

    /**
     * Presents the response model after removing a task by updating the view model with the new list of tasks.
     *
     * @param responseModel The response model containing the updated list of tasks after removal.
     */
    @Override
    public void present(RemoveTaskResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task removed: " + task.getTitle()));
    }

    /**
     * Presents the response model after completing a task by updating the view model with the new list of tasks.
     * It identifies the completed task and updates its status in the view model.
     *
     * @param responseModel The response model containing the completed task data.
     */
    @Override
    public void present(CompleteTaskResponseModel responseModel) {
        TaskData completedTask = responseModel.getTaskData();
        List<TaskData> updatedTasks = viewModel.getTasks().stream()
                .map(task -> task.getId().equals(completedTask.getId()) ? completedTask : task)
                .collect(Collectors.toList());
        viewModel.setTasks(updatedTasks);
        System.out.println("Task completed: " + completedTask.getTitle());
    }

    /**
     * Presents the response model after sorting tasks by updating the view model with the sorted list of tasks.
     *
     * @param responseModel The response model containing the sorted list of tasks.
     */
    @Override
    public void present(SortTasksResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task sorted: " + task.getTitle()));
    }

    /**
     * Presents the response model after loading the to-do list by updating the view model with the list of tasks.
     *
     * @param responseModel The response model containing the loaded list of tasks.
     */
    @Override
    public void present(LoadTodoListResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task loaded: " + task.getTitle()));
    }

    /**
     * Presents the response model after filtering tasks by updating the view model with the filtered list of tasks.
     *
     * @param responseModel The response model containing the filtered list of tasks.
     */
    @Override
    public void present(FilterTasksResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task filtered: " + task.getTitle()));
    }

    /**
     * Returns the view model associated with this presenter.
     *
     * @return The TodoListViewModel that this presenter updates.
     */
    public TodoListViewModel getViewModel() {
        return viewModel;
    }
}
