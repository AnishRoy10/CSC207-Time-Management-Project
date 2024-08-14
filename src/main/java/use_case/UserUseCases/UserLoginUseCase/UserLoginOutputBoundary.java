package use_case.UserUseCases.UserLoginUseCase;

/**
 * Output Boundary interface for the user login use case.
 * This interface is implemented by the presenter to handle the response of the login process.
 */
public interface UserLoginOutputBoundary {

    /**
     * Presents the login response after attempting to authenticate the user.
     *
     * @param responseModel The response model containing the result of the login attempt, including success status and a message.
     */
    void present(UserLoginResponseModel responseModel);
}
