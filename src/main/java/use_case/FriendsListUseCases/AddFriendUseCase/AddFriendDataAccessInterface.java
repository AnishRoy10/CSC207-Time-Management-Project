package use_case.FriendsListUseCases.AddFriendUseCase;

import entity.User;

import java.io.IOException;

/**
 * interface for the data access object of the add friend use case
 */
public interface AddFriendDataAccessInterface {
    /**
     * Writes a given user object to a file
     * @param user
     * @throws IOException
     */
    void writeUser(User user) throws IOException;

    /**
     * Loads a given user from a file given their username
     * @param username
     * @return
     * @throws IOException
     */
    User loadUser(String username) throws IOException;
}
