package use_case.TodoListUseCases.CompleteTaskUseCase;

import use_case.TodoListUseCases.TaskData;
import java.util.UUID;

/**
 * The {@code CompleteTaskResponseModel} class represents the response data after a task has been successfully completed.
 * It contains the data of the completed task and its unique identifier.
 */
public class CompleteTaskResponseModel {
    private final TaskData taskData;
    private UUID taskId;

    /**
     * Constructs a new {@code CompleteTaskResponseModel} with the specified task data.
     *
     * @param taskData The data of the completed task.
     */
    public CompleteTaskResponseModel(TaskData taskData) {
        this.taskData = taskData;
    }

    /**
     * Constructs a new {@code CompleteTaskResponseModel} with the specified task data and task ID.
     *
     * @param taskData The data of the completed task.
     * @param taskId   The unique identifier of the completed task.
     */
    public CompleteTaskResponseModel(TaskData taskData, UUID taskId) {
        this.taskData = taskData;
        this.taskId = taskId;
    }

    /**
     * Returns the data of the completed task.
     *
     * @return The task data.
     */
    public TaskData getTaskData() {
        return taskData;
    }

    /**
     * Returns the unique identifier of the completed task.
     *
     * @return The task ID.
     */
    public UUID getTaskId() {
        return taskId;
    }

    /**
     * Sets the unique identifier of the completed task.
     *
     * @param taskId The task ID to set.
     */
    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }
}
