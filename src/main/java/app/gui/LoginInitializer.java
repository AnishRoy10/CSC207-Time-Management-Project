package app.gui;

import data_access.FileCacheUserDataAccessObject;
import framework.view.UserLoginView;
import interface_adapter.controller.UserLoginController;
import interface_adapter.presenter.UserLoginPresenter;
import interface_adapter.viewmodel.UserLoginViewModel;
import use_case.UserLoginUseCase.UserLoginInputBoundary;
import use_case.UserLoginUseCase.UserLoginOutputBoundary;
import use_case.UserLoginUseCase.UserLoginUseCase;

import java.io.IOException;

/**
 * Initializer class for the user login GUI.
 */
public class LoginInitializer {
    public static void main(String[] args) {
        try {
            FileCacheUserDataAccessObject userRepository = new FileCacheUserDataAccessObject();
            UserLoginViewModel userLoginViewModel = new UserLoginViewModel();
            UserLoginOutputBoundary userLoginPresenter = new UserLoginPresenter(userLoginViewModel);
            UserLoginInputBoundary userLoginUseCase = new UserLoginUseCase(userRepository, userLoginPresenter);
            UserLoginController userLoginController = new UserLoginController(userLoginUseCase);

            UserLoginView loginView = new UserLoginView(userLoginController, userLoginViewModel);
            loginView.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing the login system.");
        }
    }
}
