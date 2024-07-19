package use_case.TodoListUseCases.CompleteTaskUseCase;

/**
 * Request model for completing a task.
 */
public class CompleteTaskRequestModel {
    private final int taskId;

    public CompleteTaskRequestModel(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }
}
