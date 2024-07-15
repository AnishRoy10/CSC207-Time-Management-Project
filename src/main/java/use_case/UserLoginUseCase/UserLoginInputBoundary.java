package use_case.UserLoginUseCase;

/**
 * Input Boundary interface for the user login use case.
 */
public interface UserLoginInputBoundary {
    /**
     * Executes the login process.
     *
     * @param requestModel The request model containing login details.
     */
    void login(UserLoginRequestModel requestModel);
}
