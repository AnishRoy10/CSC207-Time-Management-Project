package use_case.TodoListUseCases.CompleteTaskUseCase;

/**
 * Request model for completing a task.
 */
public class CompleteTaskRequestModel {
    private int taskId;
    private String username;

    public CompleteTaskRequestModel(int taskId, String username) {
        this.taskId = taskId;
        this.username = username;
    }

    // Getters and setters

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
