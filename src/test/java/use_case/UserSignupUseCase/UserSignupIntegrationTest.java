package use_case.UserSignupUseCase;

import entity.User;
import data_access.FileCacheLeaderboardDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import interface_adapter.controller.UserSignupController;
import interface_adapter.presenter.UserSignupPresenter;
import interface_adapter.viewmodel.UserSignupViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;
import repositories.UserRepository;
import use_case.UserUseCases.UserSignupUseCase.UserSignupUseCase;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Integration test for the UserSignupUseCase to test the interaction between the controller, use case, and presenter.
class UserSignupIntegrationTest {
    private FileCacheUserDataAccessObject userRepository;
    private FileCacheLeaderboardDataAccessObject leaderboardRepository;
    private UserSignupPresenter userSignupPresenter;
    private UserSignupViewModel userSignupViewModel;
    private UserSignupController userSignupController;
    private File testUserFile;
    private File testLeaderboardFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create temporary files for testing
        testUserFile = File.createTempFile("userCacheTest", ".txt");
        testLeaderboardFile = File.createTempFile("leaderboardCacheTest", ".txt");

        // Initialize the repositories with the test file paths
        userRepository = new FileCacheUserDataAccessObject(testUserFile.getAbsolutePath());
        leaderboardRepository = new FileCacheLeaderboardDataAccessObject(testLeaderboardFile.getAbsolutePath());

        userSignupViewModel = new UserSignupViewModel();
        userSignupPresenter = new UserSignupPresenter(userSignupViewModel);
        UserSignupUseCase userSignupUseCase = new UserSignupUseCase(userRepository, userSignupPresenter, leaderboardRepository);
        userSignupController = new UserSignupController(userSignupUseCase);
    }

    @AfterEach
    void tearDown() {
        // Delete the test files after each test
        if (testUserFile.exists()) {
            testUserFile.delete();
        }
        if (testLeaderboardFile.exists()) {
            testLeaderboardFile.delete();
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

        UserSignupUseCase userSignupUseCase = new UserSignupUseCase(userRepository, userSignupPresenter, leaderboardRepository);
        userSignupController = new UserSignupController(userSignupUseCase);
        userSignupController.signup("username", "Password1", "Password1");
        assertEquals("An error occurred during sign up.", userSignupViewModel.getMessage());

        // Delete the corrupted file
        corruptedFile.delete();
    }
}
