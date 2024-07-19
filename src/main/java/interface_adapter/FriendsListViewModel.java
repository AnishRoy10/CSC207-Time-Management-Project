package interface_adapter;
import entity.FriendsList;

public class FriendsListViewModel{
    private FriendsList friendsList;

    public FriendsListViewModel() {
        friendsList = new FriendsList(null);
    }
    public FriendsList getFriendsList() {
        return friendsList;
    }
    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
    }
}
