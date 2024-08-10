package use_case.TodoListUseCases.CompleteTaskUseCase;

import use_case.TodoListUseCases.TaskData;
import java.util.UUID;

/**
 * Response model for completing a task.
 */
public class CompleteTaskResponseModel {
    private final TaskData taskData;
    private UUID taskId;

    public CompleteTaskResponseModel(TaskData taskData) {
        this.taskData = taskData;
    }

    public CompleteTaskResponseModel(TaskData taskData, UUID taskId) {
        this.taskData = taskData;
        this.taskId = taskId;
    }

    public TaskData getTaskData() {
        return taskData;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }
}
