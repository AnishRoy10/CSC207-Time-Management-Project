package interface_adapter.viewmodel;
import entity.FriendsList;

import javax.swing.*;

/**
 * Viewmodel for the friends list system, used to send data to the view.
 */
public class FriendsListViewModel{
    private FriendsList friendsList;
    private DefaultListModel<String> displayedListModel;

    public FriendsListViewModel() {
        this.friendsList = new FriendsList(null);
        this.displayedListModel = new DefaultListModel<>();
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }

    public DefaultListModel<String> getDisplayedListModel() {
        return displayedListModel;
    }

    public void setFriendsList(FriendsList newFriendsList) {
        friendsList = newFriendsList;
    }

    public void setDisplayedListModel(DefaultListModel<String> newDisplayedListModel) {
        displayedListModel = newDisplayedListModel;
    }
}
