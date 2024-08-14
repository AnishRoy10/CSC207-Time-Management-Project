package use_case.TodoListUseCases.RemoveTaskUseCase;

import java.util.UUID;

/**
 * The {@code RemoveTaskRequestModel} class represents the data needed to remove a task.
 * It contains details such as the task ID, the username of the user, and optionally the course name associated with the task.
 */
public class RemoveTaskRequestModel {
    private UUID taskId;
    private String username;
    private String courseName;

    /**
     * Constructs a new {@code RemoveTaskRequestModel} with the specified task ID and username.
     *
     * @param taskId   The unique identifier of the task to be removed.
     * @param username The username of the user who owns the task.
     */
    public RemoveTaskRequestModel(UUID taskId, String username) {
        this.taskId = taskId;
        this.username = username;
        this.courseName = null; // default to null
    }

    /**
     * Constructs a new {@code RemoveTaskRequestModel} with the specified task ID, username, and course name.
     *
     * @param taskId     The unique identifier of the task to be removed.
     * @param username   The username of the user who owns the task.
     * @param courseName The name of the course associated with the task, if any.
     */
    public RemoveTaskRequestModel(UUID taskId, String username, String courseName) {
        this.taskId = taskId;
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

    /**
     * Returns the unique identifier of the task to be removed.
     *
     * @return The task ID.
     */
    public UUID getTaskId() {
        return taskId;
    }

    /**
     * Sets the unique identifier of the task to be removed.
     *
     * @param taskId The task ID to set.
     */
    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    /**
     * Returns the username of the user who owns the task.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user who owns the task.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the name of the course associated with the task, if any.
     *
     * @return The course name, or {@code null} if none is set.
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
