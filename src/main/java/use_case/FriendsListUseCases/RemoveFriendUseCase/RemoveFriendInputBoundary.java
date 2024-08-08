package use_case.FriendsListUseCases.RemoveFriendUseCase;

import java.io.IOException;

/**
 * Input boundary for the remove friend use case
 */
public interface RemoveFriendInputBoundary {
    /**
     * executes the use case
     * @param inputData
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void execute(RemoveFriendInputData inputData) throws IOException, ClassNotFoundException;
}
