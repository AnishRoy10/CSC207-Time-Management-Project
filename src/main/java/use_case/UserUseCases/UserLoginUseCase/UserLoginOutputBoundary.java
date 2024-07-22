package use_case.UserUseCases.UserLoginUseCase;

/**
 * Output Boundary interface for the user login use case.
 */
public interface UserLoginOutputBoundary {
    /**
     * Presents the login response.
     *
     * @param responseModel The response model containing the result of the login attempt.
     */
    void present(UserLoginResponseModel responseModel);
}
