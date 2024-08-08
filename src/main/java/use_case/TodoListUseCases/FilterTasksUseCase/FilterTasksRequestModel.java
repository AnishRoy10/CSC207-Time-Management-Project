package use_case.TodoListUseCases.FilterTasksUseCase;

/**
 * Request model for filtering tasks.
 */
public class FilterTasksRequestModel {
    private boolean hideCompleted;
    private String username;
    private String courseName;

    public FilterTasksRequestModel(boolean hideCompleted, String username) {
        this.hideCompleted = hideCompleted;
        this.username = username;
        this.courseName = null; // default to null
    }

    public FilterTasksRequestModel(boolean hideCompleted, String username, String courseName) {
        this.hideCompleted = hideCompleted;
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

    public boolean isHideCompleted() {
        return hideCompleted;
    }

    public void setHideCompleted(boolean hideCompleted) {
        this.hideCompleted = hideCompleted;
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
