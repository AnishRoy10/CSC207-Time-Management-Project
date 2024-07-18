package use_case.SortTasksUseCase;

/**
 * Request model for sorting tasks.
 */
public class SortTasksRequestModel {
    private String criteria;
    private boolean ascending;

    public SortTasksRequestModel(String criteria, boolean ascending) {
        this.criteria = criteria;
        this.ascending = ascending;
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
}
