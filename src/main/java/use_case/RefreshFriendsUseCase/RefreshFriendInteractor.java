package use_case.RefreshFriendsUseCase;

import entity.FriendsList;
import entity.User;
import data_access.FileCacheUserDataAccessObject;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RefreshFriendInteractor implements RefreshFriendsInputBoundary{
    private final User user;
    private final FileCacheUserDataAccessObject dao;
    public RefreshFriendInteractor(RefreshFriendsOutputBoundary outputBoundary) throws IOException, ClassNotFoundException {
        this.dao = new FileCacheUserDataAccessObject();
        this.user = dao.ReadFromCache();
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
    }
}
