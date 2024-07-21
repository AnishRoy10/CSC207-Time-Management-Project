package use_case.UserLoginUseCase;

import entity.User;
import entity.Course;
import interface_adapter.controller.UserLoginController;
import interface_adapter.presenter.UserLoginPresenter;
import interface_adapter.viewmodel.UserLoginViewModel;
import interface_adapter.viewmodel.UserSignupViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.UserRepository;
import use_case.UserUseCases.UserLoginUseCase.UserLoginUseCase;
import use_case.UserUseCases.UserSignupUseCase.UserSignupOutputBoundary;
import use_case.UserUseCases.UserSignupUseCase.UserSignupRequestModel;
import use_case.UserUseCases.UserSignupUseCase.UserSignupResponseModel;
import use_case.UserUseCases.UserSignupUseCase.UserSignupUseCase;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserLoginIntegrationTest {
    private UserRepository userRepository;
    private UserLoginController userLoginController;
    private UserSignupUseCase userSignupUseCase;
    private UserLoginViewModel userLoginViewModel;
    private UserSignupViewModel userSignupViewModel;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userLoginViewModel = new UserLoginViewModel();
        userSignupViewModel = new UserSignupViewModel();
        UserLoginPresenter userLoginPresenter = new UserLoginPresenter(userLoginViewModel);
        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, userLoginPresenter);
        userLoginController = new UserLoginController(userLoginUseCase);

        UserSignupOutputBoundary userSignupOutputBoundary = new UserSignupOutputBoundary() {
            @Override
            public void present(UserSignupResponseModel responseModel) {
                userSignupViewModel.setMessage(responseModel.getMessage());
            }
        };
        userSignupUseCase = new UserSignupUseCase(userRepository, userSignupOutputBoundary);
    }

    @Test
    void signupAndLoginUserSuccessfully() throws IOException, ClassNotFoundException {
        // Mock behavior for user repository
        when(userRepository.UserExists("username")).thenReturn(false);
        doNothing().when(userRepository).WriteToCache(any(User.class));
        when(userRepository.findByUsername("username")).thenReturn(new User("username", "Password1", new User[0], new Course[0]));

        // Sign up user
        UserSignupRequestModel signupRequestModel = new UserSignupRequestModel("username", "Password1", "Password1");
        userSignupUseCase.signup(signupRequestModel);

        // Log in user
        userLoginController.login("username", "Password1");

        // Verify login message
        assertEquals("Login successful.", userLoginViewModel.getMessage());
    }

    @Test
    void loginUserWithWrongPassword() throws IOException, ClassNotFoundException {
        // Mock behavior for user repository
        when(userRepository.findByUsername("username")).thenReturn(new User("username", "Password1", new User[0], new Course[0]));

        // Log in user with wrong password
        userLoginController.login("username", "WrongPassword");

        // Verify login message
        assertEquals("Invalid username or password.", userLoginViewModel.getMessage());
    }

    @Test
    void loginUserThatDoesNotExist() throws IOException, ClassNotFoundException {
        // Mock behavior for user repository
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // Log in user that does not exist
        userLoginController.login("nonexistent", "Password1");

        // Verify login message
        assertEquals("Invalid username or password.", userLoginViewModel.getMessage());
    }
}
