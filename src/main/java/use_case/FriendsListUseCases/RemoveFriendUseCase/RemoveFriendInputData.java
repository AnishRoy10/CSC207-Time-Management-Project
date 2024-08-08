package use_case.FriendsListUseCases.RemoveFriendUseCase;

/**
 * Class representing the input data for the remove friend use case
 */
public class RemoveFriendInputData {
    private String friend_name;

    /**
     * constructor method
     * @param friend_name
     */
    public RemoveFriendInputData(String friend_name) {
        this.friend_name = friend_name;
    }

    /**
     * getter method returning a string representing a username
     * @return
     */
    public String getFriend_name() {
        return friend_name;
    }
}
