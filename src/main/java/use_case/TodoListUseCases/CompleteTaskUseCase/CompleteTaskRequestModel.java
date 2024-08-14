package use_case.TodoListUseCases.CompleteTaskUseCase;

import java.util.UUID;

/**
 * The {@code CompleteTaskRequestModel} class represents the data needed to complete a task in the to-do list.
 * It contains details such as the task's ID, the username of the user, and optionally the course name associated with the task.
 */
public class CompleteTaskRequestModel {
    private UUID taskId;
    private String username;
    private String courseName;

    /**
     * Constructs a new {@code CompleteTaskRequestModel} with the specified task ID and username.
     *
     * @param taskId   The ID of the task to be completed.
     * @param username The username of the user completing the task.
     */
    public CompleteTaskRequestModel(UUID taskId, String username) {
        this.taskId = taskId;
        this.username = username;
        this.courseName = null; // default to null
    }

    /**
     * Constructs a new {@code CompleteTaskRequestModel} with the specified task ID, username, and course name.
     *
     * @param taskId     The ID of the task to be completed.
     * @param username   The username of the user completing the task.
     * @param courseName The name of the course associated with the task.
     */
    public CompleteTaskRequestModel(UUID taskId, String username, String courseName) {
        this.taskId = taskId;
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

    /**
     * Returns the ID of the task to be completed.
     *
     * @return The task ID.
     */
    public UUID getTaskId() {
        return taskId;
    }

    /**
     * Sets the ID of the task to be completed.
     *
     * @param taskId The task ID to set.
     */
    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    /**
     * Returns the username of the user completing the task.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user completing the task.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the name of the course associated with the task, if any.
     *
     * @return The course name, or null if none is set.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course associated with the task.
     *
     * @param courseName The course name to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
