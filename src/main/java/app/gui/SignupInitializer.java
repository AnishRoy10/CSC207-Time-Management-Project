package app.gui;

import data_access.FileCacheUserDataAccessObject;
import framework.view.UserSignupView;
import interface_adapter.controller.UserSignupController;
import interface_adapter.presenter.UserSignupPresenter;
import interface_adapter.viewmodel.UserSignupViewModel;
import use_case.UserSignupUseCase.UserSignupUseCase;

import java.io.IOException;

/**
 * Initializes the signup view and its dependencies.
 */
public class SignupInitializer {
    public static void main(String[] args) {
        try {
            // Initialize the repository
            FileCacheUserDataAccessObject userRepository = new FileCacheUserDataAccessObject();

            // Initialize the view model
            UserSignupViewModel signupViewModel = new UserSignupViewModel();

            // Initialize the presenter
            UserSignupPresenter signupPresenter = new UserSignupPresenter(signupViewModel);

            // Initialize the use case interactor
            UserSignupUseCase signupUseCase = new UserSignupUseCase(userRepository, signupPresenter);

            // Initialize the controller
            UserSignupController signupController = new UserSignupController(signupUseCase);

            // Initialize and show the view
            UserSignupView signupView = new UserSignupView(signupController, signupViewModel);
            signupView.setVisible(true);
        } catch (IOException e) {
            System.err.println("An error occurred while initializing the application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}