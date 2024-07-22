package use_case.FriendsListUseCases.AddFriendUseCase;

import entity.FriendsList;
import entity.User;
public class AddFriendOutputData {
    private final FriendsList friendsList;

    public AddFriendOutputData(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }
}
