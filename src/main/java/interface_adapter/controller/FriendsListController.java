package interface_adapter.controller;


import use_case.AddFriendUseCase.AddFriendInputBoundary;
import use_case.RefreshFriendsUseCase.RefreshFriendsInputBoundary;
import use_case.RemoveFriendUseCase.RemoveFriendInputBoundary;

public class FriendsListController {
    private final AddFriendInputBoundary addFriendUseCase;
    private final RefreshFriendsInputBoundary refreshFriendsUseCase;
    private final RemoveFriendInputBoundary removeFriendUseCase;

    public FriendsListController(AddFriendInputBoundary addFriendUseCase, RefreshFriendsInputBoundary refreshFriendsUseCase, RemoveFriendInputBoundary refreshFriendUseCase, RemoveFriendInputBoundary removeFriendUseCase) {
        this.addFriendUseCase = addFriendUseCase;
        this.refreshFriendsUseCase = refreshFriendsUseCase;
        this.removeFriendUseCase = removeFriendUseCase;
    }
    public void addFriend(String friendName) {

    }
}
