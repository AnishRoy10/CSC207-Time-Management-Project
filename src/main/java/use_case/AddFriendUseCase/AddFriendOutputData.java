package use_case.AddFriendUseCase;

import entity.FriendsList;
import entity.User;

public class AddFriendOutputData {
    private User user;
    private FriendsList friendsList;

    public AddFriendOutputData(User user, FriendsList friendsList) {
        this.user = user;
        this.friendsList = friendsList;
    }
    public User getUser() {
        return user;
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }
}
