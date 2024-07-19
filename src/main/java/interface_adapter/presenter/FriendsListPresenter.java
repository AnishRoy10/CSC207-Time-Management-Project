package interface_adapter.presenter;
import interface_adapter.FriendsListViewModel;
import use_case.AddFriendUseCase.AddFriendOutputBoundary;
import use_case.AddFriendUseCase.AddFriendOutputData;
import use_case.RefreshFriendsUseCase.RefreshFriendsOutputBoundary;
import use_case.RemoveFriendUseCase.RemoveFriendOutputBoundary;

public class FriendsListPresenter implements AddFriendOutputBoundary, RefreshFriendsOutputBoundary, RemoveFriendOutputBoundary {
    private final FriendsListViewModel viewModel;

    public FriendsListPresenter(FriendsListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(AddFriendOutputData outputData) {
        viewModel.setFriendsList(outputData.getFriendsList());
    }
}
