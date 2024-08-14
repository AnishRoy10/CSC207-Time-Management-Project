package use_case.TodoListUseCases.FilterTasksUseCase;

/**
 * The {@code FilterTasksOutputBoundary} interface defines the contract for the output boundary
 * of the Filter Tasks use case. This interface is responsible for handling the output data generated
 * by the use case, including presenting the filtered tasks.
 */
public interface FilterTasksOutputBoundary {

    /**
     * Presents the response model containing the filtered tasks.
     *
     * @param responseModel The {@link FilterTasksResponseModel} containing the filtered tasks to be presented.
     */
    void present(FilterTasksResponseModel responseModel);
}
