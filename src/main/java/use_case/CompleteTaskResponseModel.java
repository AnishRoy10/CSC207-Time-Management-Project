package use_case;

import java.util.List;

/**
 * Response model for completing a task.
 */
public class CompleteTaskResponseModel {
    private List<TaskData> tasks;

    public CompleteTaskResponseModel(List<TaskData> tasks) {
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
