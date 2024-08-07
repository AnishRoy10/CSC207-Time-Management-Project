package interface_adapter.presenter;
import interface_adapter.viewmodel.FriendsListViewModel;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendOutputBoundary;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendOutputData;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendOutputData;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendsOutputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendOutputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendOutputData;

/**
 * Presenter class for the friends list system, used to update the viewmodel.
 */
public class FriendsListPresenter implements AddFriendOutputBoundary, RefreshFriendsOutputBoundary, RemoveFriendOutputBoundary {
    private final FriendsListViewModel viewModel;

    /**
     * Constructor method
     * @param viewModel
     */
    public FriendsListPresenter(FriendsListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * sends the friendslist from the add friend use cases output data to the viewmodel
     * @param outputData
     */
    @Override
    public void present(AddFriendOutputData outputData) {
        viewModel.setFriendsList(outputData.getFriendsList());
    }

    /**
     * sends the friendslist and display model from the refresh friends use cases output data to the viewmodel
     * @param outputData
     */
    @Override
    public void present(RefreshFriendOutputData outputData) {
        viewModel.setFriendsList(outputData.getFriendsList());
        viewModel.setDisplayedListModel(outputData.getModel());
    }

    /**
     * sends the friendslist from the remove friend use cases output data to the viewmodel
     * @param outputData
     */
    @Override
    public void present(RemoveFriendOutputData outputData) {
        viewModel.setFriendsList(outputData.getFriendsList());
    }
}
