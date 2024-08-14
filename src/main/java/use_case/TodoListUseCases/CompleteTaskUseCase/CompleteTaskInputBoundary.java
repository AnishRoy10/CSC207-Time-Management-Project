package use_case.TodoListUseCases.CompleteTaskUseCase;

/**
 * The {@code CompleteTaskInputBoundary} interface defines the contract for the input boundary
 * of the Complete Task use case. This interface is responsible for accepting input data for
 * completing a task and invoking the corresponding use case.
 */
public interface CompleteTaskInputBoundary {

    /**
     * Executes the complete task use case with the given request model.
     *
     * @param requestModel The {@link CompleteTaskRequestModel} containing the data needed to complete a task.
     */
    void execute(CompleteTaskRequestModel requestModel);
}
