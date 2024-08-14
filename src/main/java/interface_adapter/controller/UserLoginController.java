package interface_adapter.controller;

import use_case.UserUseCases.UserLoginUseCase.UserLoginInputBoundary;
import use_case.UserUseCases.UserLoginUseCase.UserLoginRequestModel;

/**
 * Controller class for handling user login requests.
 * This class acts as the intermediary between the user interface and the login use case,
 * converting user input into a format that the use case can understand and process.
 */
public class UserLoginController {
    private final UserLoginInputBoundary userLoginInputBoundary;

    /**
     * Constructs a UserLoginController with the specified input boundary.
     *
     * @param userLoginInputBoundary The input boundary interface that defines the login use case.
     */
    public UserLoginController(UserLoginInputBoundary userLoginInputBoundary) {
        this.userLoginInputBoundary = userLoginInputBoundary;
    }

    /**
     * Initiates the login process by creating a request model with the provided username and password,
     * and passing it to the login use case for processing.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     */
    public void login(String username, String password) {
        UserLoginRequestModel requestModel = new UserLoginRequestModel(username, password);
        userLoginInputBoundary.login(requestModel);
    }
}
