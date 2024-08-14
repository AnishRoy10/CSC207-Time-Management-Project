package use_case.TodoListUseCases.RemoveTaskUseCase;

import use_case.TodoListUseCases.TaskData;

import java.util.List;
import java.util.UUID;

/**
 * The {@code RemoveTaskResponseModel} class represents the response data after a task has been removed.
 * It contains the updated list of tasks and the ID of the removed task.
 */
public class RemoveTaskResponseModel {
    private List<TaskData> tasks;
    private UUID taskId;

    /**
     * Constructs a new {@code RemoveTaskResponseModel} with the specified list of tasks.
     *
     * @param tasks The updated list of tasks after the removal.
     */
    public RemoveTaskResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs a new {@code RemoveTaskResponseModel} with the specified list of tasks and task ID.
     *
     * @param tasks  The updated list of tasks after the removal.
     * @param taskId The ID of the removed task.
     */
    public RemoveTaskResponseModel(List<TaskData> tasks, UUID taskId) {
        this.tasks = tasks;
        this.taskId = taskId;
    }

    /**
     * Returns the updated list of tasks after the removal.
     *
     * @return The list of tasks.
     */
    public List<TaskData> getTasks() {
        return tasks;
    }

    /**
     * Sets the updated list of tasks after the removal.
     *
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the ID of the removed task.
     *
     * @return The task ID.
     */
    public UUID getTaskId() {
        return taskId;
    }

    /**
     * Sets the ID of the removed task.
     *
     * @param taskId The task ID to set.
     */
    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }
}
