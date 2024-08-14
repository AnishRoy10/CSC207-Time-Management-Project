package use_case.FriendsListUseCases.RemoveFriendUseCase;

import entity.User;

import java.io.IOException;

/**
 * Interface for the data access object of the remove friend use case
 */
public interface RemoveFriendDataAccessInterface {
    /**
     * Loads the relevant user from a file given a username
     * @param username
     * @return
     * @throws IOException
     */
    User loadUser(String username) throws IOException;

    /**
     * Writes the given user object to a file
     * @param user
     * @throws IOException
     */
    void writeUser(User user) throws IOException;
}
