package use_case;

import java.util.List;

/**
 * Response model for adding a task.
 */
public class AddTaskResponseModel {
    private List<TaskData> tasks;

    public AddTaskResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
