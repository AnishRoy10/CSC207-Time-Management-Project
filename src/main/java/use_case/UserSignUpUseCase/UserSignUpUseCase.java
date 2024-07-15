package use_case.UserSignUpUseCase;

import entity.User;
import entity.Course;
import repositories.UserRepository;

import java.io.IOException;

public class UserSignUpUseCase implements UserSignupInputBoundary {
    private final UserRepository userRepository;
    private final UserSignupOutputBoundary outputBoundary;

    public UserSignUpUseCase(UserRepository userRepository, UserSignupOutputBoundary outputBoundary) {
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
