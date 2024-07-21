package interface_adapter.viewmodel;
import entity.FriendsList;

import javax.swing.*;

public class FriendsListViewModel{
    private FriendsList friendsList;
    private DefaultListModel<String> displayedListModel;

    public FriendsListViewModel() {
        friendsList = new FriendsList(null);
        displayedListModel = new DefaultListModel<>();
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }

    public DefaultListModel<String> getDisplayedListModel() {
        return displayedListModel;
    }

    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    public void setDisplayedListModel(DefaultListModel<String> displayedListModel) {
        this.displayedListModel = displayedListModel;
    }
}
