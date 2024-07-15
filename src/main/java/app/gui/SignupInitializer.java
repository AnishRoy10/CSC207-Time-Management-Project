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
    public static void initializeSignup() {
        try {
            FileCacheUserDataAccessObject userRepository = new FileCacheUserDataAccessObject();
            UserSignupViewModel signupViewModel = new UserSignupViewModel();
            UserSignupPresenter signupPresenter = new UserSignupPresenter(signupViewModel);
            UserSignupUseCase signupUseCase = new UserSignupUseCase(userRepository, signupPresenter);
            UserSignupController signupController = new UserSignupController(signupUseCase);
            UserSignupView signupView = new UserSignupView(signupController, signupViewModel);
            signupView.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing the signup system.");
        }
    }
}
