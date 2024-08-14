package use_case.TodoListUseCases.CompleteTaskUseCase;

/**
 * The {@code CompleteTaskOutputBoundary} interface defines the contract for the output boundary
 * of the Complete Task use case. This interface is responsible for handling the output data generated
 * by the use case, including presenting the response or handling errors.
 */
public interface CompleteTaskOutputBoundary {

    /**
     * Presents the response model after successfully completing a task.
     *
     * @param responseModel The {@link CompleteTaskResponseModel} containing the data to be presented.
     */
    void present(CompleteTaskResponseModel responseModel);
}
