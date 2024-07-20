package use_case.TodoListUseCases.FilterTasksUseCase;

/**
 * Request model for filtering tasks.
 */
public class FilterTasksRequestModel {
    private boolean hideCompleted;
    private String username;

    public FilterTasksRequestModel(boolean hideCompleted, String username) {
        this.hideCompleted = hideCompleted;
        this.username = username;
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
}
