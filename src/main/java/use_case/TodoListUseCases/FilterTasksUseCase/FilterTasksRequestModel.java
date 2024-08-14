package use_case.TodoListUseCases.FilterTasksUseCase;

/**
 * The {@code FilterTasksRequestModel} class represents the data needed to filter tasks in the to-do list.
 * It contains details such as whether to hide completed tasks, the username of the user,
 * and optionally the course name associated with the tasks.
 */
public class FilterTasksRequestModel {
    private boolean hideCompleted;
    private String username;
    private String courseName;

    /**
     * Constructs a new {@code FilterTasksRequestModel} with the specified hideCompleted flag and username.
     *
     * @param hideCompleted Indicates whether to hide completed tasks.
     * @param username      The username of the user whose tasks are to be filtered.
     */
    public FilterTasksRequestModel(boolean hideCompleted, String username) {
        this.hideCompleted = hideCompleted;
        this.username = username;
        this.courseName = null; // default to null
    }

    /**
     * Constructs a new {@code FilterTasksRequestModel} with the specified hideCompleted flag, username, and course name.
     *
     * @param hideCompleted Indicates whether to hide completed tasks.
     * @param username      The username of the user whose tasks are to be filtered.
     * @param courseName    The name of the course associated with the tasks.
     */
    public FilterTasksRequestModel(boolean hideCompleted, String username, String courseName) {
        this.hideCompleted = hideCompleted;
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

    /**
     * Returns whether to hide completed tasks.
     *
     * @return {@code true} if completed tasks should be hidden; otherwise, {@code false}.
     */
    public boolean isHideCompleted() {
        return hideCompleted;
    }

    /**
     * Sets whether to hide completed tasks.
     *
     * @param hideCompleted {@code true} to hide completed tasks; otherwise, {@code false}.
     */
    public void setHideCompleted(boolean hideCompleted) {
        this.hideCompleted = hideCompleted;
    }

    /**
     * Returns the username of the user whose tasks are to be filtered.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user whose tasks are to be filtered.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the name of the course associated with the tasks, if any.
     *
     * @return The course name, or {@code null} if none is set.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course associated with the tasks.
     *
     * @param courseName The course name to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
