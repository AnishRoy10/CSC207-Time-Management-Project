package use_case.RemoveFriendUseCase;


public class RemoveFriendInputData {
    private String friend_name;

    public RemoveFriendInputData(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_name() {
        return friend_name;
    }
}
