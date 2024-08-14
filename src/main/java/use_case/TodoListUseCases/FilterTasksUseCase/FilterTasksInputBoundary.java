package use_case.TodoListUseCases.FilterTasksUseCase;

/**
 * The {@code FilterTasksInputBoundary} interface defines the contract for the input boundary
 * of the Filter Tasks use case. This interface is responsible for accepting input data for
 * filtering tasks and invoking the corresponding use case.
 */
public interface FilterTasksInputBoundary {

    /**
     * Executes the filter tasks use case with the given request model.
     *
     * @param requestModel The {@link FilterTasksRequestModel} containing the data needed to filter tasks.
     */
    void execute(FilterTasksRequestModel requestModel);
}
