package use_case.UserUseCases.UserLoginUseCase;

import entity.User;
import repositories.UserRepository;

import java.io.IOException;

/**
 * Interactor class for the user login use case.
 * This class implements the business logic for authenticating a user based on their username and password.
 */
public class UserLoginUseCase implements UserLoginInputBoundary {
    private final UserRepository userRepository;
    private final UserLoginOutputBoundary userLoginOutputBoundary;

    /**
     * Constructs a {@code UserLoginUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository          The repository for accessing user data.
     * @param userLoginOutputBoundary The output boundary for handling the login response.
     */
    public UserLoginUseCase(UserRepository userRepository, UserLoginOutputBoundary userLoginOutputBoundary) {
        this.userRepository = userRepository;
        this.userLoginOutputBoundary = userLoginOutputBoundary;
    }

    /**
     * Executes the user login process using the provided request model.
     * This involves checking the provided credentials against stored user data and returning a response model.
     *
     * @param requestModel The request model containing login details such as username and password.
     */
    @Override
    public void login(UserLoginRequestModel requestModel) {
        String username = requestModel.getUsername();
        String password = requestModel.getPassword();

        try {
            User user = userRepository.findByUsername(username);

            if (user == null || !user.verifyPassword(password)) {
                UserLoginResponseModel responseModel = new UserLoginResponseModel(false, "Invalid username or password.");
                userLoginOutputBoundary.present(responseModel);
                return;
            }

            UserLoginResponseModel responseModel = new UserLoginResponseModel(true, "Login successful.");
            userLoginOutputBoundary.present(responseModel);
        } catch (IOException e) {
            UserLoginResponseModel responseModel = new UserLoginResponseModel(false, "An error occurred during login.");
            userLoginOutputBoundary.present(responseModel);
        }
    }
}
