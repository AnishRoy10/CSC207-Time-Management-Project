package use_case.TodoListUseCases.SortTasksUseCase;

/**
 * The {@code SortTasksRequestModel} class represents the data needed to sort tasks.
 * It contains details such as the sorting criteria, the order (ascending or descending),
 * the username of the user, and optionally the course name associated with the tasks.
 */
public class SortTasksRequestModel {
    private String criteria;
    private boolean ascending;
    private String username;
    private String courseName;

    /**
     * Constructs a new {@code SortTasksRequestModel} with the specified sorting criteria, order, and username.
     *
     * @param criteria  The criteria by which to sort the tasks (e.g., "title", "deadline", "course", "completion").
     * @param ascending Whether to sort in ascending order (true) or descending order (false).
     * @param username  The username of the user whose tasks are to be sorted.
     */
    public SortTasksRequestModel(String criteria, boolean ascending, String username) {
        this.criteria = criteria;
        this.ascending = ascending;
        this.username = username;
        this.courseName = null; // default to null
    }

    /**
     * Constructs a new {@code SortTasksRequestModel} with the specified sorting criteria, order, username, and course name.
     *
     * @param criteria   The criteria by which to sort the tasks (e.g., "title", "deadline", "course", "completion").
     * @param ascending  Whether to sort in ascending order (true) or descending order (false).
     * @param username   The username of the user whose tasks are to be sorted.
     * @param courseName The name of the course associated with the tasks, if any.
     */
    public SortTasksRequestModel(String criteria, boolean ascending, String username, String courseName) {
        this.criteria = criteria;
        this.ascending = ascending;
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

    /**
     * Returns the criteria by which to sort the tasks.
     *
     * @return The sorting criteria.
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     * Sets the criteria by which to sort the tasks.
     *
     * @param criteria The sorting criteria to set.
     */
    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    /**
     * Returns whether to sort in ascending order.
     *
     * @return {@code true} if sorting in ascending order, otherwise {@code false}.
     */
    public boolean isAscending() {
        return ascending;
    }

    /**
     * Sets whether to sort in ascending order.
     *
     * @param ascending {@code true} for ascending order, {@code false} for descending order.
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Returns the username of the user whose tasks are to be sorted.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user whose tasks are to be sorted.
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
