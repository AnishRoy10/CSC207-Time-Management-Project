package interface_adapter.presenter;
import interface_adapter.FriendsListViewModel;
import use_case.AddFriendUseCase.AddFriendOutputBoundary;
import use_case.AddFriendUseCase.AddFriendOutputData;
import use_case.RefreshFriendsUseCase.RefreshFriendOutputData;
import use_case.RefreshFriendsUseCase.RefreshFriendsOutputBoundary;
import use_case.RemoveFriendUseCase.RemoveFriendOutputBoundary;
import use_case.RemoveFriendUseCase.RemoveFriendOutputData;

public class FriendsListPresenter implements AddFriendOutputBoundary, RefreshFriendsOutputBoundary, RemoveFriendOutputBoundary {
    private final FriendsListViewModel viewModel;

    public FriendsListPresenter(FriendsListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(AddFriendOutputData outputData) {
        viewModel.setFriendsList(outputData.getFriendsList());
    }

    @Override
    public void present(RefreshFriendOutputData outputData) {
        viewModel.setDisplayedListModel(outputData.getModel());
    }

    @Override
    public void present(RemoveFriendOutputData outputData) {
        viewModel.setFriendsList(outputData.getFriendsList());
    }
}
