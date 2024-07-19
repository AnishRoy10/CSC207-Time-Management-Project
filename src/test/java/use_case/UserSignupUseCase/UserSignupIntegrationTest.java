package use_case.UserSignupUseCase;

import entity.User;
import data_access.FileCacheUserDataAccessObject;
import interface_adapter.controller.UserSignupController;
import interface_adapter.presenter.UserSignupPresenter;
import interface_adapter.viewmodel.UserSignupViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.UserUseCases.UserSignupUseCase.UserSignupUseCase;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Integration test for the UserSignupUseCase to test the interaction between the controller, use case, and presenter.
class UserSignupIntegrationTest {
    private FileCacheUserDataAccessObject userRepository;
    private UserSignupPresenter userSignupPresenter;
    private UserSignupViewModel userSignupViewModel;
    private UserSignupController userSignupController;
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file for testing
        testFile = File.createTempFile("userCacheTest", ".txt");

        // Initialize the repository with the test file path
        userRepository = new FileCacheUserDataAccessObject(testFile.getAbsolutePath());
        userSignupViewModel = new UserSignupViewModel();
        userSignupPresenter = new UserSignupPresenter(userSignupViewModel);
        UserSignupUseCase userSignupUseCase = new UserSignupUseCase(userRepository, userSignupPresenter);
        userSignupController = new UserSignupController(userSignupUseCase);
    }

    @AfterEach
    void tearDown() {
        // Delete the test file after each test
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void signupUserSuccessfully() throws IOException, ClassNotFoundException {
        userSignupController.signup("username", "Password1", "Password1");
        assertEquals("User signed up successfully.", userSignupViewModel.getMessage());
    }

    @Test
    void signupUserFailsUserExists() throws IOException, ClassNotFoundException {
        userSignupController.signup("username", "Password1", "Password1");
        userSignupController.signup("username", "Password1", "Password1");
        assertEquals("Username already exists.", userSignupViewModel.getMessage());
    }

    @Test
    void signupUserWithEmptyUsername() {
        userSignupController.signup("", "Password1", "Password1");
        assertEquals("Invalid username or password.", userSignupViewModel.getMessage());
    }

    @Test
    void signupUserWithEmptyPassword() {
        userSignupController.signup("username", "", "");
        assertEquals("Invalid username or password.", userSignupViewModel.getMessage());
    }

    @Test
    void signupUserFailsOnRepositoryError() throws IOException, ClassNotFoundException {
        // Simulate repository error by using a corrupted file
        File corruptedFile = File.createTempFile("userCacheCorruptTest", ".txt");
        userRepository = new FileCacheUserDataAccessObject(corruptedFile.getAbsolutePath()) {
            @Override
            public void WriteToCache(User user) throws IOException {
                throw new IOException("Test exception");
            }
        };

        UserSignupUseCase userSignupUseCase = new UserSignupUseCase(userRepository, userSignupPresenter);
        userSignupController = new UserSignupController(userSignupUseCase);
        userSignupController.signup("username", "Password1", "Password1");
        assertEquals("An error occurred during sign up.", userSignupViewModel.getMessage());

        // Delete the corrupted file
        corruptedFile.delete();
    }
}
