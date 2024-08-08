package use_case.FriendsListUseCases.RefreshFriendsUseCase;

/**
 *Output boundary for the refresh friends use case
 */
public interface RefreshFriendsOutputBoundary {
    /**
     * Sends output data to the presenter
     * @param outputData
     */
    void present(RefreshFriendOutputData outputData);
}
