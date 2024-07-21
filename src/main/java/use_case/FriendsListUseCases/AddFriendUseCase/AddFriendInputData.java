package use_case.FriendsListUseCases.AddFriendUseCase;

public class AddFriendInputData {
    private String name;

    public AddFriendInputData(String name) {
        this.name = name;
    }
    public String getUser() {
        return name;
    }
}
