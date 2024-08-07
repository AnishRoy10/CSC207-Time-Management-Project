package app.gui;

import data_access.FileCacheUserDataAccessObject;
import framework.view.UserSignupView;
import interface_adapter.controller.UserSignupController;
import interface_adapter.presenter.UserSignupPresenter;
import interface_adapter.viewmodel.UserSignupViewModel;
import use_case.UserUseCases.UserSignupUseCase.UserSignupUseCase;

/**
 * This class is responsible for initializing the signup view along with its dependencies.
 * It sets up the necessary components for the signup functionality to work, including
 * the user repository, view model, presenter, use case, and controller.
 */
public class SignupInitializer {
    /**
     * Initializes the signup view and its dependencies.
     * This method sets up the user repository, view model, presenter, use case, and controller
     * for the signup functionality. It then makes the signup view visible to the user.
     */
    public static void initializeSignup() {
        try {
            // Initialize the user repository with the file path
            String filePath = "src/main/java/data_access/userCache.json";
            FileCacheUserDataAccessObject userRepository = new FileCacheUserDataAccessObject(filePath);
            // Initialize the view model for the signup view
            UserSignupViewModel signupViewModel = new UserSignupViewModel();
            // Initialize the presenter that will handle the output from the use case
            UserSignupPresenter signupPresenter = new UserSignupPresenter(signupViewModel);
            // Initialize the use case with the user repository and presenter
            UserSignupUseCase signupUseCase = new UserSignupUseCase(userRepository, signupPresenter);
            // Initialize the controller with the use case
            UserSignupController signupController = new UserSignupController(signupUseCase);

            // Initialize the signup view with the controller and view model, then make it visible
            UserSignupView signupView = new UserSignupView(signupController, signupViewModel);
            signupView.setVisible(true);
        } catch (Exception e) {
            // Print the stack trace and an error message if an exception occurs
            e.printStackTrace();
            System.out.println("Error initializing the signup system.");
        }
    }
}
