package use_case.TodoListUseCases.AddTaskUseCase;

/**
 * The {@code AddTaskOutputBoundary} interface defines the contract for the output boundary
 * of the Add Task use case. This interface is responsible for handling the output data generated
 * by the use case, including presenting the response or handling errors.
 */
public interface AddTaskOutputBoundary {

    /**
     * Presents the response model after successfully adding a task.
     *
     * @param responseModel The {@link AddTaskResponseModel} containing the data to be presented.
     */
    void present(AddTaskResponseModel responseModel);

    /**
     * Presents an error message when adding a task fails due to validation or other issues.
     *
     * @param errorMessage The error message to be presented.
     */
    void presentError(String errorMessage);
}
