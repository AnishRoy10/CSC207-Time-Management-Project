package use_case.TodoListUseCases.SortTasksUseCase;

/**
 * The {@code SortTasksInputBoundary} interface defines the contract for the input boundary
 * of the Sort Tasks use case. This interface is responsible for accepting input data
 * to sort tasks and invoking the corresponding use case.
 */
public interface SortTasksInputBoundary {

    /**
     * Executes the sort tasks use case with the given request model.
     *
     * @param requestModel The {@link SortTasksRequestModel} containing the data needed to sort the tasks.
     */
    void execute(SortTasksRequestModel requestModel);
}
