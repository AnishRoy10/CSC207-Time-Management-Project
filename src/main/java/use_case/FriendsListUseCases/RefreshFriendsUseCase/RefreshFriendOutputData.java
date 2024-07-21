package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import javax.swing.*;

public class RefreshFriendOutputData {
    private DefaultListModel<String> model;

    public RefreshFriendOutputData(DefaultListModel<String> model) {
        this.model = model;
    }
    public DefaultListModel<String> getModel() {
        return model;
    }
}
