package use_case.FriendsListUseCases.AddFriendUseCase;

/**
 * Class representing the input data for the add friend use case
 */
public class AddFriendInputData {
    private String name;

    /**
     * constructor
     * @param name
     */
    public AddFriendInputData(String name) {
        this.name = name;
    }

    /**
     * getter method for a string representing a username
     * @return
     */
    public String getUser() {
        return name;
    }
}
