package use_case.TodoListUseCases.SortTasksUseCase;

import use_case.TodoListUseCases.TaskData;

import java.util.List;

/**
 * Response model for sorting tasks.
 */
public class SortTasksResponseModel {
    private List<TaskData> tasks;

    public SortTasksResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    // Getters and setters

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
