package use_case.UserUseCases.UserLoginUseCase;

/**
 * Input Boundary interface for the user login use case.
 * This interface is implemented by the interactor class to handle the login process.
 */
public interface UserLoginInputBoundary {

    /**
     * Executes the login process using the provided request model.
     *
     * @param requestModel The request model containing login details, including the username and password.
     */
    void login(UserLoginRequestModel requestModel);
}
