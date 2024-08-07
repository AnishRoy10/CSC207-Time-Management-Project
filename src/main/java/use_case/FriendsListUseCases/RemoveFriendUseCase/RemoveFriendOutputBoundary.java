package use_case.FriendsListUseCases.RemoveFriendUseCase;

/**
 * Output boundary for the remove friend use case
 */
public interface RemoveFriendOutputBoundary {
    /**
     * sends output data to the presenter
     * @param outputData
     */
    void present(RemoveFriendOutputData outputData);
}
