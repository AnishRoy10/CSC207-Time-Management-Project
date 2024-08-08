package use_case.TodoListUseCases.LoadTodoListUseCase;

/**
 * Request model for loading the to-do list.
 */
public class LoadTodoListRequestModel {
    private String username;
    private String courseName;

    public LoadTodoListRequestModel(String username) {
        this.username = username;
        this.courseName = null; // default to null
    }

    public LoadTodoListRequestModel(String username, String courseName) {
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

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
