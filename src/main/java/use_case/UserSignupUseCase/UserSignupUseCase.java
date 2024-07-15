package use_case.UserSignupUseCase;

import entity.User;
import entity.Course;
import repositories.UserRepository;

import java.io.IOException;

/**
 * Use case for the user sign-up process.
 */
public class UserSignupUseCase implements UserSignupInputBoundary {
    private final UserRepository userRepository;
    private final UserSignupOutputBoundary outputBoundary;

    /**
     * Constructs a UserSignupUseCase with the specified repository and output boundary.
     *
     * @param userRepository  The repository to access user data.
     * @param outputBoundary  The output boundary to present the response.
     */
    public UserSignupUseCase(UserRepository userRepository, UserSignupOutputBoundary outputBoundary) {
        this.userRepository = userRepository;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void signup(UserSignupRequestModel requestModel) {
        try {
            if (userRepository.UserExists(requestModel.getUsername())) {
                outputBoundary.present(new UserSignupResponseModel(false, "Username already exists."));
            } else {
                User newUser = new User(requestModel.getUsername(), requestModel.getPassword(), new User[0], new Course[0]);
                userRepository.WriteToCache(newUser);
                outputBoundary.present(new UserSignupResponseModel(true, "User signed up successfully."));
            }
        } catch (IOException | ClassNotFoundException e) {
            outputBoundary.present(new UserSignupResponseModel(false, "An error occurred during sign up."));
        }
    }
}
