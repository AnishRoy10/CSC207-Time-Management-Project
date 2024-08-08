package use_case.FriendsListUseCases.RemoveFriendUseCase;

import entity.FriendsList;

/**
 * Class representing the output data for the remove friend use case
 */
public class RemoveFriendOutputData{
    private FriendsList friendsList;

    /**
     * constructor method
     * @param friendsList
     */
    public RemoveFriendOutputData(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    /**
     * getter method that returns the output datas friendlist
     * @return
     */
    public FriendsList getFriendsList() {
        return friendsList;
    }
}
