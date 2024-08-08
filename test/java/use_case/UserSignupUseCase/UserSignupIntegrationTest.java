package use_case.UserSignupUseCase;

import entity.User;
import data_access.SQLDatabaseHelper;
import data_access.SQLLeaderboardDAO;
import data_access.UserDAO;
import interface_adapter.controller.UserSignupController;
import interface_adapter.presenter.UserSignupPresenter;
import interface_adapter.viewmodel.UserSignupViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repositories.LeaderboardRepository;
import repositories.UserRepository;
import use_case.UserUseCases.UserSignupUseCase.UserSignupUseCase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Integration test for the UserSignupUseCase to test the interaction between the controller, use case, and presenter.
class UserSignupIntegrationTest {
    private SQLDatabaseHelper dbHelper;
    private UserRepository userRepository;
    private LeaderboardRepository leaderboardRepository;
    private UserSignupPresenter userSignupPresenter;
    private UserSignupViewModel userSignupViewModel;
    private UserSignupController userSignupController;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userRepository = new UserDAO(dbHelper);
        leaderboardRepository = new SQLLeaderboardDAO(dbHelper);

        userSignupViewModel = new UserSignupViewModel();
        userSignupPresenter = new UserSignupPresenter(userSignupViewModel);
        UserSignupUseCase userSignupUseCase = new UserSignupUseCase(userRepository, userSignupPresenter, leaderboardRepository);
        userSignupController = new UserSignupController(userSignupUseCase);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Users");
            stmt.execute("DELETE FROM Leaderboard");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void signupUserSuccessfully() {
        userSignupController.signup("username", "Password1", "Password1");
        assertEquals("User signed up successfully.", userSignupViewModel.getMessage());
    }

    @Test
    void signupUserFailsUserExists() {
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
}
