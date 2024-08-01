package use_case.UserSignupUseCase;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import repositories.UserRepository;
import use_case.UserUseCases.UserSignupUseCase.UserSignupOutputBoundary;
import use_case.UserUseCases.UserSignupUseCase.UserSignupRequestModel;
import use_case.UserUseCases.UserSignupUseCase.UserSignupResponseModel;
import use_case.UserUseCases.UserSignupUseCase.UserSignupUseCase;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserSignupUseCaseTest {
    private UserRepository userRepository;
    private UserSignupOutputBoundary userSignupOutputBoundary;
    private UserSignupUseCase userSignupUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userSignupOutputBoundary = Mockito.mock(UserSignupOutputBoundary.class);
        userSignupUseCase = new UserSignupUseCase(userRepository, userSignupOutputBoundary);
    }

    @Test
    void signupUserSuccessfully() throws IOException, ClassNotFoundException {
        when(userRepository.UserExists("username")).thenReturn(false);
        doNothing().when(userRepository).WriteToCache(any(User.class));

        UserSignupRequestModel requestModel = new UserSignupRequestModel("username", "Password1", "Password1");
        userSignupUseCase.signup(requestModel);

        ArgumentCaptor<UserSignupResponseModel> captor = ArgumentCaptor.forClass(UserSignupResponseModel.class);
        verify(userSignupOutputBoundary).present(captor.capture());
        UserSignupResponseModel responseModel = captor.getValue();

        assertEquals("User signed up successfully.", responseModel.getMessage());
    }

    @Test
    void signupUserFailsUserExists() throws IOException, ClassNotFoundException {
        when(userRepository.UserExists("username")).thenReturn(true);

        UserSignupRequestModel requestModel = new UserSignupRequestModel("username", "Password1", "Password1");
        userSignupUseCase.signup(requestModel);

        ArgumentCaptor<UserSignupResponseModel> captor = ArgumentCaptor.forClass(UserSignupResponseModel.class);
        verify(userSignupOutputBoundary).present(captor.capture());
        UserSignupResponseModel responseModel = captor.getValue();

        assertEquals("Username already exists.", responseModel.getMessage());
    }

    @Test
    void signupUserWithEmptyUsername() throws IOException, ClassNotFoundException {
        UserSignupRequestModel requestModel = new UserSignupRequestModel("", "Password1", "Password1");
        userSignupUseCase.signup(requestModel);

        ArgumentCaptor<UserSignupResponseModel> captor = ArgumentCaptor.forClass(UserSignupResponseModel.class);
        verify(userSignupOutputBoundary).present(captor.capture());
        UserSignupResponseModel responseModel = captor.getValue();

        assertEquals("Invalid username or password.", responseModel.getMessage());
    }

    @Test
    void signupUserWithEmptyPassword() throws IOException, ClassNotFoundException {
        UserSignupRequestModel requestModel = new UserSignupRequestModel("username", "", "");
        userSignupUseCase.signup(requestModel);

        ArgumentCaptor<UserSignupResponseModel> captor = ArgumentCaptor.forClass(UserSignupResponseModel.class);
        verify(userSignupOutputBoundary).present(captor.capture());
        UserSignupResponseModel responseModel = captor.getValue();

        assertEquals("Invalid username or password.", responseModel.getMessage());
    }

    @Test
    void signupUserWithNonMatchingPasswords() throws IOException, ClassNotFoundException {
        UserSignupRequestModel requestModel = new UserSignupRequestModel("username", "Password1", "Password2");
        userSignupUseCase.signup(requestModel);

        ArgumentCaptor<UserSignupResponseModel> captor = ArgumentCaptor.forClass(UserSignupResponseModel.class);
        verify(userSignupOutputBoundary).present(captor.capture());
        UserSignupResponseModel responseModel = captor.getValue();

        assertEquals("Passwords do not match.", responseModel.getMessage());
    }

    @Test
    void signupUserFailsOnRepositoryError() throws IOException, ClassNotFoundException {
        when(userRepository.UserExists("username")).thenReturn(false);
        doThrow(new IOException("Test exception")).when(userRepository).WriteToCache(any(User.class));

        UserSignupRequestModel requestModel = new UserSignupRequestModel("username", "Password1", "Password1");
        userSignupUseCase.signup(requestModel);

        ArgumentCaptor<UserSignupResponseModel> captor = ArgumentCaptor.forClass(UserSignupResponseModel.class);
        verify(userSignupOutputBoundary).present(captor.capture());
        UserSignupResponseModel responseModel = captor.getValue();

        assertEquals("An error occurred during sign up.", responseModel.getMessage());
    }
}
