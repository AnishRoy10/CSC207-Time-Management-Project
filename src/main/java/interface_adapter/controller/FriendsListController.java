package interface_adapter.controller;



import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputBoundary;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputData;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendInputData;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendsInputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputData;

import java.io.IOException;

public class FriendsListController {
    private final AddFriendInputBoundary addFriendUseCase;
    private final RefreshFriendsInputBoundary refreshFriendsUseCase;
    private final RemoveFriendInputBoundary removeFriendUseCase;


    public FriendsListController(AddFriendInputBoundary addFriendUseCase, RefreshFriendsInputBoundary refreshFriendsUseCase, RemoveFriendInputBoundary removeFriendUseCase) throws IOException, ClassNotFoundException {
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
