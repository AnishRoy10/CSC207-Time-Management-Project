package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import entity.FriendsList;
import entity.User;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Use case interactor for refreshing the friends list display
 */
public class RefreshFriendInteractor implements RefreshFriendsInputBoundary{
    private User user;
    private final RefreshFriendsOutputBoundary presenter;
    private final RefreshFriendsDataAccessInterface dao;
    private final String username;
    public RefreshFriendInteractor(RefreshFriendsOutputBoundary outputBoundary, RefreshFriendsDataAccessInterface dao, String username) throws IOException, ClassNotFoundException {
        this.presenter = outputBoundary;
        this.dao = dao;
        this.username = username;
        this.user = dao.loadUser(username);
    };

    /**
     * Refreshes the active users friends list display
     * @param inputData
     * @throws IOException
     */
    @Override
    public void execute(RefreshFriendInputData inputData) throws IOException {
        user = dao.loadUser(username);
        if(user == null){
            return;
        }
        FriendsList friendsList = user.getFriends();
        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<String> friendsUsernames = friendsList.exportFriendsNames();
        for (int i = 0; i < friendsUsernames.size(); i++){
            model.addElement(friendsUsernames.get(i));
            System.out.println(friendsUsernames.get(i));
        }
        RefreshFriendOutputData outputData = new RefreshFriendOutputData(model, user);
        presenter.present(outputData);
    }
}
