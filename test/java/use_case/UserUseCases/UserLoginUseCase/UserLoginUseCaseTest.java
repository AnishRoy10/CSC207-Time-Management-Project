package use_case.UserUseCases.UserLoginUseCase;

import entity.User;
import entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import repositories.UserRepository;
import use_case.UserUseCases.UserLoginUseCase.UserLoginOutputBoundary;
import use_case.UserUseCases.UserLoginUseCase.UserLoginRequestModel;
import use_case.UserUseCases.UserLoginUseCase.UserLoginResponseModel;
import use_case.UserUseCases.UserLoginUseCase.UserLoginUseCase;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserLoginUseCaseTest {
    private UserRepository userRepository;
    private UserLoginOutputBoundary userLoginOutputBoundary;
    private UserLoginUseCase userLoginUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userLoginOutputBoundary = Mockito.mock(UserLoginOutputBoundary.class);
        userLoginUseCase = new UserLoginUseCase(userRepository, userLoginOutputBoundary);
    }

    @Test
    void loginUserSuccessfully() throws IOException, ClassNotFoundException {
        User user = new User("username", "Password1", new User[0], new Course[0]);
        when(userRepository.findByUsername("username")).thenReturn(user);

        UserLoginRequestModel requestModel = new UserLoginRequestModel("username", "Password1");
        userLoginUseCase.login(requestModel);

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary).present(captor.capture());
        UserLoginResponseModel responseModel = captor.getValue();

        assertEquals("Login successful.", responseModel.getMessage());
        assertEquals(true, responseModel.isSuccess());
    }

    @Test
    void loginUserWithIncorrectUsername() throws IOException, ClassNotFoundException {
        when(userRepository.findByUsername("wrongUsername")).thenReturn(null);

        UserLoginRequestModel requestModel = new UserLoginRequestModel("wrongUsername", "Password1");
        userLoginUseCase.login(requestModel);

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary).present(captor.capture());
        UserLoginResponseModel responseModel = captor.getValue();

        assertEquals("Invalid username or password.", responseModel.getMessage());
        assertEquals(false, responseModel.isSuccess());
    }

    @Test
    void loginUserWithIncorrectPassword() throws IOException, ClassNotFoundException {
        User user = new User("username", "Password1", new User[0], new Course[0]);
        when(userRepository.findByUsername("username")).thenReturn(user);

        UserLoginRequestModel requestModel = new UserLoginRequestModel("username", "WrongPassword");
        userLoginUseCase.login(requestModel);

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary).present(captor.capture());
        UserLoginResponseModel responseModel = captor.getValue();

        assertEquals("Invalid username or password.", responseModel.getMessage());
        assertEquals(false, responseModel.isSuccess());
    }

    @Test
    void loginUserWithException() throws IOException, ClassNotFoundException {
        when(userRepository.findByUsername("username")).thenThrow(new IOException("Test exception"));

        UserLoginRequestModel requestModel = new UserLoginRequestModel("username", "Password1");
        userLoginUseCase.login(requestModel);

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary).present(captor.capture());
        UserLoginResponseModel responseModel = captor.getValue();

        assertEquals("An error occurred during login.", responseModel.getMessage());
        assertEquals(false, responseModel.isSuccess());
    }

    @Test
    void loginUserAfterPasswordChange() throws IOException, ClassNotFoundException {
        User user = new User("username", "NewPassword1", new User[0], new Course[0]);
        when(userRepository.findByUsername("username")).thenReturn(user);

        UserLoginRequestModel requestModel = new UserLoginRequestModel("username", "NewPassword1");
        userLoginUseCase.login(requestModel);

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary).present(captor.capture());
        UserLoginResponseModel responseModel = captor.getValue();

        assertEquals("Login successful.", responseModel.getMessage());
        assertEquals(true, responseModel.isSuccess());
    }

    @Test
    void testConcurrentLogins() throws IOException, ClassNotFoundException {
        User user = new User("username", "Password1", new User[0], new Course[0]);
        when(userRepository.findByUsername("username")).thenReturn(user);

        Runnable loginTask = () -> {
            UserLoginRequestModel requestModel = new UserLoginRequestModel("username", "Password1");
            userLoginUseCase.login(requestModel);
        };

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

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary, times(2)).present(captor.capture());
        assertEquals("Login successful.", captor.getAllValues().get(0).getMessage());
    }

    @Test
    void loginUserWithEmptyUsername() throws IOException, ClassNotFoundException {
        UserLoginRequestModel requestModel = new UserLoginRequestModel("", "Password1");
        userLoginUseCase.login(requestModel);

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary).present(captor.capture());
        UserLoginResponseModel responseModel = captor.getValue();

        assertEquals("Invalid username or password.", responseModel.getMessage());
        assertEquals(false, responseModel.isSuccess());
    }

    @Test
    void loginUserWithEmptyPassword() throws IOException, ClassNotFoundException {
        User user = new User("username", "Password1", new User[0], new Course[0]);
        when(userRepository.findByUsername("username")).thenReturn(user);

        UserLoginRequestModel requestModel = new UserLoginRequestModel("username", "");
        userLoginUseCase.login(requestModel);

        ArgumentCaptor<UserLoginResponseModel> captor = ArgumentCaptor.forClass(UserLoginResponseModel.class);
        verify(userLoginOutputBoundary).present(captor.capture());
        UserLoginResponseModel responseModel = captor.getValue();

        assertEquals("Invalid username or password.", responseModel.getMessage());
        assertEquals(false, responseModel.isSuccess());
    }

}
