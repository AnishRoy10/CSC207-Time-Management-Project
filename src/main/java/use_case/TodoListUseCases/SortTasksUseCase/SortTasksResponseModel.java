package use_case.TodoListUseCases.SortTasksUseCase;

import use_case.TodoListUseCases.TaskData;

import java.util.List;

/**
 * The {@code SortTasksResponseModel} class represents the response data after tasks have been sorted.
 * It contains the sorted list of tasks.
 */
public class SortTasksResponseModel {
    private List<TaskData> tasks;

    /**
     * Constructs a new {@code SortTasksResponseModel} with the specified list of sorted tasks.
     *
     * @param tasks The sorted list of tasks.
     */
    public SortTasksResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    // Getters and setters

    /**
     * Returns the sorted list of tasks.
     *
     * @return The list of tasks.
     */
    public List<TaskData> getTasks() {
        return tasks;
    }

    /**
     * Sets the sorted list of tasks.
     *
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
