package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import data_access.FriendsListDataAccessObject;
import entity.FriendsList;
import entity.User;
import data_access.FileCacheUserDataAccessObject;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class RefreshFriendInteractor implements RefreshFriendsInputBoundary{
    private final User user;
    private final RefreshFriendsOutputBoundary presenter;
    private final RefreshFriendsDataAccessInterface dao;
    public RefreshFriendInteractor(RefreshFriendsOutputBoundary outputBoundary, RefreshFriendsDataAccessInterface dao, String username) throws IOException, ClassNotFoundException {
        this.dao = dao;
        this.presenter = outputBoundary;
        this.user = dao.loadUser(username);
    };
    @Override
    public void execute(RefreshFriendInputData inputData) {
        if(user == null){
            return;
        }
        FriendsList friendsList = user.getFriends();
        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<String> friendsUsernames = friendsList.exportFriendsNames();
        for (int i = 0; i < friendsUsernames.size(); i++){
            model.addElement(friendsUsernames.get(i));
        }
        RefreshFriendOutputData outputData = new RefreshFriendOutputData(model);
    }
}
