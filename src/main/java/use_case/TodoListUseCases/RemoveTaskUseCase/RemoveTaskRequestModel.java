package use_case.TodoListUseCases.RemoveTaskUseCase;

/**
 * Request model for removing a task.
 */
public class RemoveTaskRequestModel {
    private int taskId;
    private String username;

    public RemoveTaskRequestModel(int taskId, String username) {
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
