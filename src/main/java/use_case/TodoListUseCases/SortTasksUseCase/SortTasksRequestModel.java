package use_case.TodoListUseCases.SortTasksUseCase;

/**
 * Request model for sorting tasks.
 */
public class SortTasksRequestModel {
    private String criteria;
    private boolean ascending;
    private String username;

    public SortTasksRequestModel(String criteria, boolean ascending, String username) {
        this.criteria = criteria;
        this.ascending = ascending;
        this.username = username;
    }

    // Getters and setters

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
