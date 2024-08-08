package use_case.FriendsListUseCases.AddFriendUseCase;

/**
 * Output boundary for the add friend use case
 */
public interface AddFriendOutputBoundary {
    /**
     * sends output data to the presenter
     * @param outputData
     */
    void present(AddFriendOutputData outputData);
}
