package use_case.TodoListUseCases.RemoveTaskUseCase;

/**
 * Request model for removing a task.
 */
public class RemoveTaskRequestModel {
    private int taskId;

    public RemoveTaskRequestModel(int taskId) {
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
