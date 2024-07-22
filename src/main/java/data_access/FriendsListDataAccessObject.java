package data_access;

import entity.User;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendDataAccessInterface;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendsDataAccessInterface;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendDataAccessInterface;

import java.io.IOException;

public class FriendsListDataAccessObject implements AddFriendDataAccessInterface, RefreshFriendsDataAccessInterface, RemoveFriendDataAccessInterface {
    private final FileCacheUserDataAccessObject dao;

    public FriendsListDataAccessObject(FileCacheUserDataAccessObject dao) {
        this.dao = dao;
    }

    @Override
    public void writeUser(User user) throws IOException {
        dao.WriteToCache(user);
    }

    @Override
    public User loadUser(String username) throws IOException {
        return dao.findByUsername(username);
    }
}
