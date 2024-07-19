package interface_adapter.presenter;

import use_case.UserUseCases.UserSignupUseCase.UserSignupOutputBoundary;
import use_case.UserUseCases.UserSignupUseCase.UserSignupResponseModel;
import interface_adapter.viewmodel.UserSignupViewModel;

/**
 * Presenter for handling user signup responses.
 */
public class UserSignupPresenter implements UserSignupOutputBoundary {

    private final UserSignupViewModel userSignupViewModel;

    /**
     * Constructor for UserSignupPresenter.
     *
     * @param userSignupViewModel The view model for user signup.
     */
    public UserSignupPresenter(UserSignupViewModel userSignupViewModel) {
        this.userSignupViewModel = userSignupViewModel;
    }

    /**
     * Presents the signup response model to the view model.
     *
     * @param responseModel The response model containing the signup result.
     */
    @Override
    public void present(UserSignupResponseModel responseModel) {
        userSignupViewModel.setSignupSuccess(responseModel.isSuccess());
        userSignupViewModel.setMessage(responseModel.getMessage());
    }
}
