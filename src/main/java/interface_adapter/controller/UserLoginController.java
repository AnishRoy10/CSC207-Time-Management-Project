package interface_adapter.controller;

import use_case.UserLoginUseCase.UserLoginInputBoundary;
import use_case.UserLoginUseCase.UserLoginRequestModel;

/**
 * Controller class for user login.
 */
public class UserLoginController {
    private final UserLoginInputBoundary userLoginInputBoundary;

    /**
     * Constructor for UserLoginController.
     *
     * @param userLoginInputBoundary The input boundary for user login use case.
     */
    public UserLoginController(UserLoginInputBoundary userLoginInputBoundary) {
        this.userLoginInputBoundary = userLoginInputBoundary;
    }

    /**
     * Initiates the login process.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     */
    public void login(String username, String password) {
        UserLoginRequestModel requestModel = new UserLoginRequestModel(username, password);
        userLoginInputBoundary.login(requestModel);
    }
}