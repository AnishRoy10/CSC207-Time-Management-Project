package use_case.TodoListUseCases.AddTaskUseCase;

public interface AddTaskOutputBoundary {
    void present(AddTaskResponseModel responseModel);

    void presentError(String errorMessage); // New method for error handling
}

