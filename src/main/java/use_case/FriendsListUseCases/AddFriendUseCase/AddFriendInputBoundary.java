package use_case.FriendsListUseCases.AddFriendUseCase;

import java.io.IOException;

/**
 * Input boundary for the add friend use cases input data
 */
public interface AddFriendInputBoundary {
    /**
     * Executes the add friend use case
     * @param inputData
     * @throws IOException
     */
    void execute(AddFriendInputData inputData) throws IOException;
}
