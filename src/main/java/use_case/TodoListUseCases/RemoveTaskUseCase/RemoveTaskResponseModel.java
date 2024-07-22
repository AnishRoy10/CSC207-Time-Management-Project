package use_case.TodoListUseCases.RemoveTaskUseCase;

import use_case.TaskData;

import java.util.List;
import java.util.UUID;

/**
 * Response model for removing a task.
 */
public class RemoveTaskResponseModel {
    private List<TaskData> tasks;
    private UUID taskId;

    public RemoveTaskResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public RemoveTaskResponseModel(List<TaskData> tasks, UUID taskId) {
        this.tasks = tasks;
        this.taskId = taskId;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }
}
