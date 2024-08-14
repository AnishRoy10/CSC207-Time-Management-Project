package interface_adapter.controller;

import use_case.UserUseCases.UserSignupUseCase.UserSignupInputBoundary;
import use_case.UserUseCases.UserSignupUseCase.UserSignupRequestModel;

/**
 * Controller class for handling user signup requests.
 * This class serves as the intermediary between the user interface and the signup use case,
 * facilitating the conversion of user input into a format suitable for processing by the use case.
 */
public class UserSignupController {
    private final UserSignupInputBoundary userSignupInputBoundary;

    /**
     * Constructs a UserSignupController with the specified input boundary.
     *
     * @param userSignupInputBoundary The input boundary interface that defines the signup use case.
     */
    public UserSignupController(UserSignupInputBoundary userSignupInputBoundary) {
        this.userSignupInputBoundary = userSignupInputBoundary;
    }

    /**
     * Initiates the signup process by creating a request model with the provided username, password, and confirmation password,
     * and passing it to the signup use case for processing.
     *
     * @param username       The username provided by the user.
     * @param password       The password provided by the user.
     * @param confirmPassword The confirmation password provided by the user to verify the password match.
     */
    public void signup(String username, String password, String confirmPassword) {
        UserSignupRequestModel requestModel = new UserSignupRequestModel(username, password, confirmPassword);
        userSignupInputBoundary.signup(requestModel);
    }
}
