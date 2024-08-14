package use_case.UserUseCases.UserSignupUseCase;

import repositories.LeaderboardRepository;
import entity.User;
import entity.Course;
import repositories.UserRepository;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interactor class for the user signup use case.
 * This class implements the business logic for signing up a new user, including validation and interaction with repositories.
 */
public class UserSignupUseCase implements UserSignupInputBoundary {
    private static final Logger LOGGER = Logger.getLogger(UserSignupUseCase.class.getName());
    private final UserRepository userRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final UserSignupOutputBoundary userSignupOutputBoundary;

    /**
     * Constructs a {@code UserSignupUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository           The repository for user data access.
     * @param userSignupOutputBoundary The output boundary for user signup response.
     * @param leaderboardRepository    The repository for leaderboard data access.
     */
    public UserSignupUseCase(UserRepository userRepository, UserSignupOutputBoundary userSignupOutputBoundary, LeaderboardRepository leaderboardRepository) {
        this.userRepository = userRepository;
        this.userSignupOutputBoundary = userSignupOutputBoundary;
        this.leaderboardRepository = leaderboardRepository;
    }

    /**
     * Executes the user signup process using the provided request model.
     * This includes validating the username and password, checking if the username already exists,
     * and saving the new user to the repository if validation is successful.
     *
     * @param requestModel The request model containing signup details.
     */
    @Override
    public void signup(UserSignupRequestModel requestModel) {
        String username = requestModel.getUsername();
        String password = requestModel.getPassword();
        String confirmPassword = requestModel.getConfirmPassword();

        // Validate username and password
        if (!isValidUsername(username) || !isValidPassword(password)) {
            UserSignupResponseModel responseModel = new UserSignupResponseModel(false, "Invalid username or password.");
            userSignupOutputBoundary.present(responseModel);
            return;
        }

        if (!password.equals(confirmPassword)) {
            UserSignupResponseModel responseModel = new UserSignupResponseModel(false, "Passwords do not match.");
            userSignupOutputBoundary.present(responseModel);
            return;
        }

        try {
            if (userRepository.UserExists(username)) {
                UserSignupResponseModel responseModel = new UserSignupResponseModel(false, "Username already exists.");
                userSignupOutputBoundary.present(responseModel);
                return;
            }

            User newUser = new User(username, password, new User[0], new Course[0]);
            userRepository.WriteToCache(newUser);
            leaderboardRepository.addNewUser(username);

            UserSignupResponseModel responseModel = new UserSignupResponseModel(true, "User signed up successfully.");
            userSignupOutputBoundary.present(responseModel);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error during sign up", e);
            UserSignupResponseModel responseModel = new UserSignupResponseModel(false, "An error occurred during sign up.");
            userSignupOutputBoundary.present(responseModel);
        }
    }

    /**
     * Validates the format of the username.
     *
     * @param username The username to validate.
     * @return {@code true} if the username is valid, otherwise {@code false}.
     */
    private boolean isValidUsername(String username) {
        // Current temporary validation logic: username must be between 3 and 15 characters and contain only alphanumeric characters.
        return username != null && username.matches("^[a-zA-Z0-9]{3,15}$");
    }

    /**
     * Validates the format of the password.
     *
     * @param password The password to validate.
     * @return {@code true} if the password is valid, otherwise {@code false}.
     */
    private boolean isValidPassword(String password) {
        // Current temporary validation logic: password must be at least 6 characters long and contain at least one number and one letter.
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");
    }
}
