package interface_adapter.viewmodel;
import entity.FriendsList;

import javax.swing.*;

/**
 * Viewmodel for the friends list system, used to send data to the view.
 */
public class FriendsListViewModel{
    private FriendsList friendsList;
    private DefaultListModel<String> displayedListModel;

    /**
     * Constructor method
     */
    public FriendsListViewModel() {
        this.friendsList = new FriendsList(null);
        this.displayedListModel = new DefaultListModel<>();
    }

    /**
     * Getter method to return the viewmodels friendslist
     * @return
     */
    public FriendsList getFriendsList() {
        return friendsList;
    }

    /**
     * Getter method to return the viewmodels displayed
     * @return
     */
    public DefaultListModel<String> getDisplayedListModel() {
        return displayedListModel;
    }

    /**
     * Setter method for the viewmodels friendslist
     * @param newFriendsList
     */
    public void setFriendsList(FriendsList newFriendsList) {
        friendsList = newFriendsList;
    }

    /**
     * Setter method for the viewmodels displayedlistmodel
     * @param newDisplayedListModel
     */
    public void setDisplayedListModel(DefaultListModel<String> newDisplayedListModel) {
        displayedListModel = newDisplayedListModel;
    }
}
