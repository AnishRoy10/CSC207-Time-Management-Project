package use_case.TodoListUseCases.LoadTodoListUseCase;

/**
 * The {@code LoadTodoListInputBoundary} interface defines the contract for the input boundary
 * of the Load To-Do List use case. This interface is responsible for accepting input data
 * to load the to-do list and invoking the corresponding use case.
 */
public interface LoadTodoListInputBoundary {

    /**
     * Executes the load to-do list use case with the given request model.
     *
     * @param requestModel The {@link LoadTodoListRequestModel} containing the data needed to load the to-do list.
     */
    void execute(LoadTodoListRequestModel requestModel);
}
