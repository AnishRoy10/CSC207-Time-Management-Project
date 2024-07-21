package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import entity.FriendsList;
import entity.User;

import javax.swing.*;

public class RefreshFriendOutputData {
    private DefaultListModel<String> model;
    private User user;

    public RefreshFriendOutputData(DefaultListModel<String> model, User user) {
        this.model = model;
        this.user = user;
    }
    public DefaultListModel<String> getModel() {
        return model;
    }
    public FriendsList getFriendsList() {
        return user.getFriends();
    }
}
