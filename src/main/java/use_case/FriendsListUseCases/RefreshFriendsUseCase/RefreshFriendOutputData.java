package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import entity.FriendsList;
import entity.User;

import javax.swing.*;

/**
 * Class representing the output data for the refresh friend use case
 */
public class RefreshFriendOutputData {
    private DefaultListModel<String> model;
    private User user;

    /**
     * Constructor method
     * @param model
     * @param user
     */
    public RefreshFriendOutputData(DefaultListModel<String> model, User user) {
        this.model = model;
        this.user = user;
    }

    /**
     * Returns a default list model to update the viewmodel
     * @return
     */
    public DefaultListModel<String> getModel() {
        return model;
    }

    /**
     * Returns a friendslist object
     * @return
     */
    public FriendsList getFriendsList() {
        return user.getFriends();
    }
}
