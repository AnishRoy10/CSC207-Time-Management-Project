package data_access;

import entity.User;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendDataAccessInterface;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendsDataAccessInterface;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendDataAccessInterface;

import java.io.IOException;

/**
 * Data access object for the friendslist use cases
 */
public class FriendsListDataAccessObject implements AddFriendDataAccessInterface, RefreshFriendsDataAccessInterface, RemoveFriendDataAccessInterface {
    private final FileCacheUserDataAccessObject dao;

    /**
     * constructor
     * @param dao
     */
    public FriendsListDataAccessObject(FileCacheUserDataAccessObject dao) {
        this.dao = dao;
    }

    /**
     * Writes a given user object to a file
     * @param user
     * @throws IOException
     */
    @Override
    public void writeUser(User user) throws IOException {
        dao.WriteToCache(user);
    }

    /**
     * Reads a file and returns the user with the given username if they exist
     * @param username
     * @return
     * @throws IOException
     */
    @Override
    public User loadUser(String username) throws IOException {
        return dao.updateAndFindByUsername(username);
    }
}
