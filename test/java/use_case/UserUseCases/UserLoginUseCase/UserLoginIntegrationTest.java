package use_case.UserUseCases.UserLoginUseCase;

import entity.User;
import entity.Course;
import interface_adapter.controller.UserLoginController;
import interface_adapter.presenter.UserLoginPresenter;
import interface_adapter.viewmodel.UserLoginViewModel;
import interface_adapter.viewmodel.UserSignupViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;
import repositories.UserRepository;
import use_case.UserUseCases.UserLoginUseCase.UserLoginUseCase;
import use_case.UserUseCases.UserSignupUseCase.UserSignupOutputBoundary;
import use_case.UserUseCases.UserSignupUseCase.UserSignupRequestModel;
import use_case.UserUseCases.UserSignupUseCase.UserSignupResponseModel;
import use_case.UserUseCases.UserSignupUseCase.UserSignupUseCase;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserLoginIntegrationTest {
    private UserRepository userRepository;
    private UserLoginController userLoginController;
    private UserSignupUseCase userSignupUseCase;
    private UserLoginViewModel userLoginViewModel;
    private UserSignupViewModel userSignupViewModel;
    private LeaderboardRepository leaderboardRepository;

    @BeforeEach
    void setUp() throws IOException {
        userRepository = mock(UserRepository.class);
        leaderboardRepository = mock(LeaderboardRepository.class);

        userLoginViewModel = new UserLoginViewModel();
        userSignupViewModel = new UserSignupViewModel();

        UserLoginPresenter userLoginPresenter = new UserLoginPresenter(userLoginViewModel);
        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, userLoginPresenter);
        userLoginController = new UserLoginController(userLoginUseCase);

        UserSignupOutputBoundary userSignupOutputBoundary = responseModel -> userSignupViewModel.setMessage(responseModel.getMessage());
        userSignupUseCase = new UserSignupUseCase(userRepository, userSignupOutputBoundary, leaderboardRepository);
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

    @Test
    void loginUserAfterChangingPassword() throws IOException, ClassNotFoundException {
        // Mock behavior for user repository
        User user = new User("username", "NewPassword1", new User[0], new Course[0]);
        when(userRepository.findByUsername("username")).thenReturn(user);

        // Log in user with new password
        userLoginController.login("username", "NewPassword1");

        // Verify login message
        assertEquals("Login successful.", userLoginViewModel.getMessage());
    }

    @Test
    void testConcurrentLogins() throws IOException, ClassNotFoundException {
        // Mock behavior for user repository
        User user = new User("username", "Password1", new User[0], new Course[0]);
        when(userRepository.findByUsername("username")).thenReturn(user);

        // Simulate concurrent logins
        Runnable loginTask = () -> userLoginController.login("username", "Password1");

        Thread thread1 = new Thread(loginTask);
        Thread thread2 = new Thread(loginTask);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify login message
        assertEquals("Login successful.", userLoginViewModel.getMessage());
    }

    @Test
    void loginUserWithEmptyUsername() {
        // Log in user with empty username
        userLoginController.login("", "Password1");

        // Verify login message
        assertEquals("Invalid username or password.", userLoginViewModel.getMessage());
    }

    @Test
    void loginUserWithEmptyPassword() {
        // Log in user with empty password
        userLoginController.login("username", "");

        // Verify login message
        assertEquals("Invalid username or password.", userLoginViewModel.getMessage());
    }
}
