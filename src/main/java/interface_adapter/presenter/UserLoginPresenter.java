package interface_adapter.presenter;

import use_case.UserUseCases.UserLoginUseCase.UserLoginOutputBoundary;
import use_case.UserUseCases.UserLoginUseCase.UserLoginResponseModel;
import interface_adapter.viewmodel.UserLoginViewModel;

/**
 * The UserLoginPresenter class is responsible for handling the presentation logic
 * for the user login use case. It updates the view model with the login response
 * details, making the data available for the view layer.
 */
public class UserLoginPresenter implements UserLoginOutputBoundary {
    private final UserLoginViewModel userLoginViewModel;

    /**
     * Constructs a UserLoginPresenter with the specified view model.
     *
     * @param userLoginViewModel The ViewModel for user login, which will be updated
     *                           with the response from the use case.
     */
    public UserLoginPresenter(UserLoginViewModel userLoginViewModel) {
        this.userLoginViewModel = userLoginViewModel;
    }

    /**
     * Presents the response from the user login use case by updating the view model
     * with the appropriate message.
     *
     * @param responseModel The response model containing the result of the login
     *                      attempt, including whether it was successful and any
     *                      accompanying message.
     */
    @Override
    public void present(UserLoginResponseModel responseModel) {
        userLoginViewModel.setMessage(responseModel.getMessage());
    }
}
