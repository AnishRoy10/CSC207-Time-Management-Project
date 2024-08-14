package use_case.TodoListUseCases.LoadTodoListUseCase;

import use_case.TodoListUseCases.TaskData;
import java.util.List;

/**
 * The {@code LoadTodoListResponseModel} class represents the response data after a to-do list has been loaded.
 * It contains the list of tasks that were loaded.
 */
public class LoadTodoListResponseModel {
    private final List<TaskData> tasks;

    /**
     * Constructs a new {@code LoadTodoListResponseModel} with the specified list of loaded tasks.
     *
     * @param tasks The list of tasks that were loaded.
     */
    public LoadTodoListResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the list of loaded tasks.
     *
     * @return The list of tasks.
     */
    public List<TaskData> getTasks() {
        return tasks;
    }
}
