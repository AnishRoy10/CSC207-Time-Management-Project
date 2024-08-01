package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import java.io.IOException;

/**
 * Input boundary for the refresh friends use case
 */
public interface RefreshFriendsInputBoundary {
    /**
     * Executes the use case
     * @param inputData
     * @throws IOException
     */
    void execute(RefreshFriendInputData inputData) throws IOException;
}
