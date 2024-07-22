package use_case.TodoListUseCases.LoadTodoListUseCase;

/**
 * Request model for loading the to-do list.
 */
public class LoadTodoListRequestModel {
    private String username;

    public LoadTodoListRequestModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
