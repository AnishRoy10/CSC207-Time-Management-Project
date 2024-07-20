package use_case.RemoveFriendUseCase;

import entity.FriendsList;

public class RemoveFriendOutputData{
    private FriendsList friendsList;
    public RemoveFriendOutputData(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }
}
