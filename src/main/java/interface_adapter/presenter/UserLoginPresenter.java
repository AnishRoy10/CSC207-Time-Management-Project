package interface_adapter.presenter;

import use_case.UserLoginUseCase.UserLoginOutputBoundary;
import use_case.UserLoginUseCase.UserLoginResponseModel;
import interface_adapter.viewmodel.UserLoginViewModel;

/**
 * Presenter class for user login.
 */
public class UserLoginPresenter implements UserLoginOutputBoundary {
    private final UserLoginViewModel userLoginViewModel;

    /**
     * Constructor for UserLoginPresenter.
     *
     * @param userLoginViewModel The ViewModel for user login.
     */
    public UserLoginPresenter(UserLoginViewModel userLoginViewModel) {
        this.userLoginViewModel = userLoginViewModel;
    }

    /**
     * Presents the response from the user login use case.
     *
     * @param responseModel The response model containing login details.
     */
    @Override
    public void present(UserLoginResponseModel responseModel) {
        userLoginViewModel.setMessage(responseModel.getMessage());
    }
}
