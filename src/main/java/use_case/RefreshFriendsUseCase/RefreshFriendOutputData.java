package use_case.RefreshFriendsUseCase;

import javax.swing.*;

public class RefreshFriendOutputData {
    private DefaultListModel<String> model;

    public RefreshFriendOutputData() {
        this.model = new DefaultListModel<>();
    }
    public DefaultListModel<String> getModel() {
        return model;
    }
}
