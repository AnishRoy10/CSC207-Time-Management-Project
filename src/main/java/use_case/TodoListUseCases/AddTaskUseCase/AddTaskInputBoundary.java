package use_case.TodoListUseCases.AddTaskUseCase;

/**
 * The {@code AddTaskInputBoundary} interface defines the contract for the input boundary
 * of the Add Task use case. This interface is responsible for accepting input data for adding
 * a task and invoking the corresponding use case.
 */
public interface AddTaskInputBoundary {

    /**
     * Executes the add task use case with the given request model.
     *
     * @param requestModel The {@link AddTaskRequestModel} containing the data needed to add a task.
     */
    void execute(AddTaskRequestModel requestModel);
}
