package use_case.FilterTasksUseCase;

import use_case.TaskData;

import java.util.List;

/**
 * Response model for filtering tasks.
 */
public class FilterTasksResponseModel {
    private List<TaskData> tasks;

    public FilterTasksResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    // Getter and setter

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
