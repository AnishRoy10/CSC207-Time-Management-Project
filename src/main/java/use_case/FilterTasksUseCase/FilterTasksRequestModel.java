package use_case.FilterTasksUseCase;

/**
 * Request model for filtering tasks.
 */
public class FilterTasksRequestModel {
    private boolean hideCompleted;

    public FilterTasksRequestModel(boolean hideCompleted) {
        this.hideCompleted = hideCompleted;
    }

    // Getter and setter

    public boolean isHideCompleted() {
        return hideCompleted;
    }

    public void setHideCompleted(boolean hideCompleted) {
        this.hideCompleted = hideCompleted;
    }
}
