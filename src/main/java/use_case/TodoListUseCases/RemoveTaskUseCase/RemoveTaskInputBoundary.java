package use_case.TodoListUseCases.RemoveTaskUseCase;

/**
 * The {@code RemoveTaskInputBoundary} interface defines the contract for the input boundary
 * of the Remove Task use case. This interface is responsible for accepting input data
 * to remove a task and invoking the corresponding use case.
 */
public interface RemoveTaskInputBoundary {

    /**
     * Executes the remove task use case with the given request model.
     *
     * @param requestModel The {@link RemoveTaskRequestModel} containing the data needed to remove the task.
     */
    void execute(RemoveTaskRequestModel requestModel);
}
