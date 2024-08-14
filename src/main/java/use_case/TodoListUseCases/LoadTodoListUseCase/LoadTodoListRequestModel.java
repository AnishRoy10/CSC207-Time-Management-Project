package use_case.TodoListUseCases.LoadTodoListUseCase;

/**
 * The {@code LoadTodoListRequestModel} class represents the data needed to load a user's to-do list.
 * It contains details such as the username of the user and optionally the course name associated with the tasks.
 */
public class LoadTodoListRequestModel {
    private String username;
    private String courseName;

    /**
     * Constructs a new {@code LoadTodoListRequestModel} with the specified username.
     *
     * @param username The username of the user whose to-do list is to be loaded.
     */
    public LoadTodoListRequestModel(String username) {
        this.username = username;
        this.courseName = null; // default to null
    }

    /**
     * Constructs a new {@code LoadTodoListRequestModel} with the specified username and course name.
     *
     * @param username   The username of the user whose to-do list is to be loaded.
     * @param courseName The name of the course associated with the tasks.
     */
    public LoadTodoListRequestModel(String username, String courseName) {
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

    /**
     * Returns the username of the user whose to-do list is to be loaded.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user whose to-do list is to be loaded.
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
