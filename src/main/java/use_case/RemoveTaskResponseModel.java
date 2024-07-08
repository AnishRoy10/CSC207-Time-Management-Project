package use_case;

import java.util.List;

/**
 * Response model for removing a task.
 */
public class RemoveTaskResponseModel {
    private List<TaskData> tasks;

    public RemoveTaskResponseModel(List<TaskData> tasks) {
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
