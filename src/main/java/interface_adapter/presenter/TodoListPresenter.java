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
 * Presenter for the to-do list, implementing the output boundaries for adding, removing, completing, sorting, loading, and filtering tasks.
 */
public class TodoListPresenter implements AddTaskOutputBoundary, RemoveTaskOutputBoundary, CompleteTaskOutputBoundary, SortTasksOutputBoundary, LoadTodoListOutputBoundary, FilterTasksOutputBoundary {

    private final TodoListViewModel viewModel;

    public TodoListPresenter(TodoListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(AddTaskResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task added: " + task.getTitle()));
    }

    @Override
    public void present(RemoveTaskResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task removed: " + task.getTitle()));
    }

    @Override
    public void present(CompleteTaskResponseModel responseModel) {
        TaskData completedTask = responseModel.getTaskData();
        List<TaskData> updatedTasks = viewModel.getTasks().stream()
                .map(task -> task.getId().equals(completedTask.getId()) ? completedTask : task)
                .collect(Collectors.toList());
        viewModel.setTasks(updatedTasks);
        System.out.println("Task completed: " + completedTask.getTitle());
    }

    @Override
    public void present(SortTasksResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task sorted: " + task.getTitle()));
    }

    @Override
    public void present(LoadTodoListResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task loaded: " + task.getTitle()));
    }

    @Override
    public void present(FilterTasksResponseModel responseModel) {
        viewModel.setTasks(responseModel.getTasks());
        responseModel.getTasks().forEach(task -> System.out.println("Task filtered: " + task.getTitle()));
    }

    public TodoListViewModel getViewModel() {
        return viewModel;
    }

}
