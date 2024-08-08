package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import entity.User;

import java.io.IOException;

/**
 * Interface for the data access object for the refresh friends use case
 */
public interface RefreshFriendsDataAccessInterface {
    /**
     * Returns the relevant user given a username, otherwise throws IOException
     * @param username
     * @return
     * @throws IOException
     */
    User loadUser(String username) throws IOException;
}
