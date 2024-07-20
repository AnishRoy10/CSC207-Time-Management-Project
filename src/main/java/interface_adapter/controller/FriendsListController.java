package interface_adapter.controller;



import use_case.AddFriendUseCase.AddFriendInputBoundary;
import use_case.AddFriendUseCase.AddFriendInputData;
import use_case.RefreshFriendsUseCase.RefreshFriendInputData;
import use_case.RefreshFriendsUseCase.RefreshFriendsInputBoundary;
import use_case.RemoveFriendUseCase.RemoveFriendInputBoundary;
import use_case.RemoveFriendUseCase.RemoveFriendInputData;

import java.io.IOException;

public class FriendsListController {
    private final AddFriendInputBoundary addFriendUseCase;
    private final RefreshFriendsInputBoundary refreshFriendsUseCase;
    private final RemoveFriendInputBoundary removeFriendUseCase;


    public FriendsListController(AddFriendInputBoundary addFriendUseCase, RefreshFriendsInputBoundary refreshFriendsUseCase, RemoveFriendInputBoundary refreshFriendUseCase, RemoveFriendInputBoundary removeFriendUseCase) throws IOException, ClassNotFoundException {
        this.addFriendUseCase = addFriendUseCase;
        this.refreshFriendsUseCase = refreshFriendsUseCase;
        this.removeFriendUseCase = removeFriendUseCase;
    }
    public void refreshFriend() throws IOException {
        RefreshFriendInputData inputData = new RefreshFriendInputData();
        refreshFriendsUseCase.execute(inputData);
    }

    public void addFriend(String name) throws IOException {
        AddFriendInputData inputData = new AddFriendInputData(name);
        addFriendUseCase.execute(inputData);
    }

    public void removeFriend(String name) throws IOException, ClassNotFoundException {
        RemoveFriendInputData inputData = new RemoveFriendInputData(name);
        removeFriendUseCase.execute(inputData);
    }
}
