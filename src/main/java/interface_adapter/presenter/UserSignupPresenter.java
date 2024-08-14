package interface_adapter.presenter;

import use_case.UserUseCases.UserSignupUseCase.UserSignupOutputBoundary;
import use_case.UserUseCases.UserSignupUseCase.UserSignupResponseModel;
import interface_adapter.viewmodel.UserSignupViewModel;

/**
 * The UserSignupPresenter class is responsible for managing the presentation logic
 * for the user signup use case. It updates the view model with the results of the
 * signup process, ensuring the view layer has access to the necessary information.
 */
public class UserSignupPresenter implements UserSignupOutputBoundary {

    private final UserSignupViewModel userSignupViewModel;

    /**
     * Constructs a UserSignupPresenter with the specified view model.
     *
     * @param userSignupViewModel The view model for user signup, which will be
     *                            updated with the results of the signup process.
     */
    public UserSignupPresenter(UserSignupViewModel userSignupViewModel) {
        this.userSignupViewModel = userSignupViewModel;
    }

    /**
     * Presents the signup response model to the view model by setting the success
     * status and message, which informs the view layer of the outcome of the signup
     * process.
     *
     * @param responseModel The response model containing the result of the signup
     *                      attempt, including whether it was successful and any
     *                      accompanying message.
     */
    @Override
    public void present(UserSignupResponseModel responseModel) {
        userSignupViewModel.setSignupSuccess(responseModel.isSuccess());
        userSignupViewModel.setMessage(responseModel.getMessage());
    }
}
