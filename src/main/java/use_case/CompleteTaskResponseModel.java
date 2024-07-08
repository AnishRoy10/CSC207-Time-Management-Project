package use_case;

/**
 * Response model for completing a task.
 */
public class CompleteTaskResponseModel {
    private final TaskData taskData;

    public CompleteTaskResponseModel(TaskData taskData) {
        this.taskData = taskData;
    }

    public TaskData getTaskData() {
        return taskData;
    }
}
