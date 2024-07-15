package use_case.UserSignupUseCase;

import entity.User;
import entity.Course;
import repositories.UserRepository;

import java.io.IOException;

/**
 * Interactor class for the user signup use case.
 */
public class UserSignupUseCase implements UserSignupInputBoundary {
    private final UserRepository userRepository;
    private final UserSignupOutputBoundary userSignupOutputBoundary;

    /**
     * Constructor for UserSignupUseCase.
     *
     * @param userRepository           The repository for user data access.
     * @param userSignupOutputBoundary The output boundary for user signup response.
     */
    public UserSignupUseCase(UserRepository userRepository, UserSignupOutputBoundary userSignupOutputBoundary) {
        this.userRepository = userRepository;
        this.userSignupOutputBoundary = userSignupOutputBoundary;
    }

    /**
     * Executes the user signup process.
     *
     * @param requestModel The request model containing signup details.
     */
    @Override
    public void signup(UserSignupRequestModel requestModel) {
        String username = requestModel.getUsername();
        String password = requestModel.getPassword();

        // Validate username and password
        if (!isValidUsername(username) || !isValidPassword(password)) {
            UserSignupResponseModel responseModel = new UserSignupResponseModel(false, "Invalid username or password.");
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

            UserSignupResponseModel responseModel = new UserSignupResponseModel(true, "User signed up successfully.");
            userSignupOutputBoundary.present(responseModel);
        } catch (IOException | ClassNotFoundException e) {
            UserSignupResponseModel responseModel = new UserSignupResponseModel(false, "An error occurred during sign up.");
            userSignupOutputBoundary.present(responseModel);
        }
    }

    /**
     * Validates the username format.
     *
     * @param username The username to validate.
     * @return True if the username is valid, otherwise false.
     */
    private boolean isValidUsername(String username) {
        // Current temporary validation logic: username must be between 3 and 15 characters and contain only
        // alphanumeric characters
        return username != null && username.matches("^[a-zA-Z0-9]{3,15}$");
    }

    /**
     * Validates the password format.
     *
     * @param password The password to validate.
     * @return True if the password is valid, otherwise false.
     */
    private boolean isValidPassword(String password) {
        // Current temporary validation logic: password must be at least 6 characters long and contain at least one
        // number and one letter
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");
    }
}