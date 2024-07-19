package interface_adapter.controller;

import use_case.UserUseCases.UserSignupUseCase.UserSignupInputBoundary;
import use_case.UserUseCases.UserSignupUseCase.UserSignupRequestModel;

/**
 * Controller for handling user signup requests.
 */
public class UserSignupController {
    private final UserSignupInputBoundary userSignupInputBoundary;

    /**
     * Constructor for UserSignupController.
     *
     * @param userSignupInputBoundary The input boundary for the user signup use case.
     */
    public UserSignupController(UserSignupInputBoundary userSignupInputBoundary) {
        this.userSignupInputBoundary = userSignupInputBoundary;
    }

    /**
     * Handles the user signup process.
     *
     * @param username       The username provided by the user.
     * @param password       The password provided by the user.
     * @param confirmPassword The confirmation password provided by the user.
     */
    public void signup(String username, String password, String confirmPassword) {
        UserSignupRequestModel requestModel = new UserSignupRequestModel(username, password, confirmPassword);
        userSignupInputBoundary.signup(requestModel);
    }
}
