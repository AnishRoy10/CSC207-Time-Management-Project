package use_case.CompleteTaskUseCase;

import use_case.TaskData;

/**
 * Response model for completing a task.
 */
public class CompleteTaskResponseModel {
    private final TaskData taskData;
    private int taskId;

    public CompleteTaskResponseModel(TaskData taskData) {
        this.taskData = taskData;
    }

    public CompleteTaskResponseModel(TaskData taskData, int taskId) {
        this.taskData = taskData;
        this.taskId = taskId;
    }

    public TaskData getTaskData() {
        return taskData;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
