package use_case.TodoListUseCases.FilterTasksUseCase;

import use_case.TodoListUseCases.TaskData;
import java.util.List;

/**
 * The {@code FilterTasksResponseModel} class represents the response data after tasks have been filtered.
 * It contains the list of tasks that match the filtering criteria.
 */
public class FilterTasksResponseModel {
    private List<TaskData> tasks;

    /**
     * Constructs a new {@code FilterTasksResponseModel} with the specified list of filtered tasks.
     *
     * @param tasks The list of filtered tasks.
     */
    public FilterTasksResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    // Getter and setter

    /**
     * Returns the list of filtered tasks.
     *
     * @return The list of tasks.
     */
    public List<TaskData> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of filtered tasks.
     *
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
