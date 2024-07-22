package use_case.UserUseCases.UserLoginUseCase;

import entity.User;
import repositories.UserRepository;

import java.io.IOException;

/**
 * Interactor class for the user login use case.
 */
public class UserLoginUseCase implements UserLoginInputBoundary {
    private final UserRepository userRepository;
    private final UserLoginOutputBoundary userLoginOutputBoundary;

    /**
     * Constructor for UserLoginUseCase.
     *
     * @param userRepository           The repository for user data access.
     * @param userLoginOutputBoundary  The output boundary for user login response.
     */
    public UserLoginUseCase(UserRepository userRepository, UserLoginOutputBoundary userLoginOutputBoundary) {
        this.userRepository = userRepository;
        this.userLoginOutputBoundary = userLoginOutputBoundary;
    }

    /**
     * Executes the user login process.
     *
     * @param requestModel The request model containing login details.
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
