package use_case.UserUseCases.UserSignupUseCase;

/**
 * Output boundary interface for the user signup use case.
 * This interface is implemented by the presenter to handle the response of the signup process.
 */
public interface UserSignupOutputBoundary {

    /**
     * Presents the result of the signup process.
     *
     * @param responseModel The response model containing the success status and message of the signup process.
     */
    void present(UserSignupResponseModel responseModel);
}
