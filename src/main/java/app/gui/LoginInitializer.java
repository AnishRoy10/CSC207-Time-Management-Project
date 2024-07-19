package app.gui;

import data_access.FileCacheUserDataAccessObject;
import framework.view.UserLoginView;
import interface_adapter.controller.UserLoginController;
import interface_adapter.presenter.UserLoginPresenter;
import interface_adapter.viewmodel.UserLoginViewModel;
import use_case.UserUseCases.UserLoginUseCase.UserLoginInputBoundary;
import use_case.UserUseCases.UserLoginUseCase.UserLoginOutputBoundary;
import use_case.UserUseCases.UserLoginUseCase.UserLoginUseCase;

import java.io.IOException;

/**
 * This class is responsible for initializing the login view along with its dependencies.
 * It sets up the necessary components for the login functionality to work, including
 * the user repository, view model, presenter, use case, and controller.
 */
public class LoginInitializer {
    /**
     * Initializes the login view and its dependencies.
     * This method sets up the user repository, view model, presenter, use case, and controller
     * for the login functionality. It then makes the login view visible to the user.
     * If an IOException occurs during initialization, it prints the stack trace and an error message.
     */
    public static void initializeLogin() {
        try {
            // Initialize the user repository
            FileCacheUserDataAccessObject userRepository = new FileCacheUserDataAccessObject();
            // Initialize the view model for the login view
            UserLoginViewModel userLoginViewModel = new UserLoginViewModel();
            // Initialize the presenter that will handle the output from the use case
            UserLoginOutputBoundary userLoginPresenter = new UserLoginPresenter(userLoginViewModel);
            // Initialize the use case with the user repository and presenter
            UserLoginInputBoundary userLoginUseCase = new UserLoginUseCase(userRepository, userLoginPresenter);
            // Initialize the controller with the use case
            UserLoginController userLoginController = new UserLoginController(userLoginUseCase);

            // Initialize the login view with the controller and view model, then make it visible
            UserLoginView loginView = new UserLoginView(userLoginController, userLoginViewModel);
            loginView.setVisible(true);
        } catch (IOException e) {
            // Print the stack trace and an error message if an IOException occurs
            e.printStackTrace();
            System.out.println("Error initializing the login system.");
        }
    }
}