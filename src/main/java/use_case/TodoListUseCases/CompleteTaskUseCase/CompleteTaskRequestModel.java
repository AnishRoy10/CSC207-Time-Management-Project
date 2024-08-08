package use_case.TodoListUseCases.CompleteTaskUseCase;

import java.util.UUID;

/**
 * Request model for completing a task.
 */
public class CompleteTaskRequestModel {
    private UUID taskId;
    private String username;
    private String courseName;

    public CompleteTaskRequestModel(UUID taskId, String username) {
        this.taskId = taskId;
        this.username = username;
        this.courseName = null; // default to null
    }

    public CompleteTaskRequestModel(UUID taskId, String username, String courseName) {
        this.taskId = taskId;
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
