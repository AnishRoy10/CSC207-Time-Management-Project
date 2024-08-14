package use_case.FriendsListUseCases.AddFriendUseCase;
/**
 * Class representing the output data for the add friend use case
 */

import entity.FriendsList;
public class AddFriendOutputData {
    private final FriendsList friendsList;

    /**
     * Constructor method
     * @param friendsList
     */
    public AddFriendOutputData(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    /**
     * Getter method for the friendslist in the output data
     * @return
     */
    public FriendsList getFriendsList() {
        return friendsList;
    }
}
