package interface_adapter.controller;



import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputBoundary;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputData;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendInputData;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendsInputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputData;

import java.io.IOException;

/**
 * Controller class for the friends list system, used to call use cases.
 */
public class FriendsListController {
    private final AddFriendInputBoundary addFriendUseCase;
    private final RefreshFriendsInputBoundary refreshFriendsUseCase;
    private final RemoveFriendInputBoundary removeFriendUseCase;


    public FriendsListController(AddFriendInputBoundary addFriendUseCase, RefreshFriendsInputBoundary refreshFriendsUseCase, RemoveFriendInputBoundary removeFriendUseCase) throws IOException, ClassNotFoundException {
        this.addFriendUseCase = addFriendUseCase;
        this.refreshFriendsUseCase = refreshFriendsUseCase;
        this.removeFriendUseCase = removeFriendUseCase;
    }

    /**
     * Refreshes the active users friends list display
     * @throws IOException
     */
    public void refreshFriend() throws IOException {
        RefreshFriendInputData inputData = new RefreshFriendInputData();
        refreshFriendsUseCase.execute(inputData);
    }

    /**
     * Adds a new friend to the users friends list
     * @param name
     * @throws IOException
     */
    public void addFriend(String name) throws IOException {
        AddFriendInputData inputData = new AddFriendInputData(name);
        addFriendUseCase.execute(inputData);
    }

    /**
     * Removes a friend from the users friends list
     * @param name
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void removeFriend(String name) throws IOException, ClassNotFoundException {
        RemoveFriendInputData inputData = new RemoveFriendInputData(name);
        removeFriendUseCase.execute(inputData);
    }
}
