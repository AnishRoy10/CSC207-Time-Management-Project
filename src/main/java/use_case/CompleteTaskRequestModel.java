package use_case;

/**
 * Request model for completing a task.
 */
public class CompleteTaskRequestModel {
    private int taskId;

    public CompleteTaskRequestModel(int taskId) {
        this.taskId = taskId;
    }

    // Getter and setter

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
