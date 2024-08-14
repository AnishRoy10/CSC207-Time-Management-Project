package use_case.UserUseCases.UserSignupUseCase;

/**
 * Input boundary interface for the user signup use case.
 * This interface is implemented by the interactor class to handle the signup process.
 */
public interface UserSignupInputBoundary {

    /**
     * Initiates the user signup process using the provided request model.
     *
     * @param requestModel The request model containing the signup details such as username, password, and confirmation password.
     */
    void signup(UserSignupRequestModel requestModel);
}
