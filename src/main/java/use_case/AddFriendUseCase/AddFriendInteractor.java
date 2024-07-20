package use_case.AddFriendUseCase;

import entity.FriendsList;
import entity.User;
import data_access.FileCacheUserDataAccessObject;

import java.io.IOException;

public class AddFriendInteractor implements AddFriendInputBoundary{
    private final FriendsList friendsList;
    private final AddFriendOutputBoundary addFriendOutputBoundary;
    private final User[] usersDb;

    public AddFriendInteractor(FriendsList friendsList, AddFriendOutputBoundary addFriendOutputBoundary, User[] usersDb) {
        this.friendsList = friendsList;
        this.addFriendOutputBoundary = addFriendOutputBoundary;
        this.usersDb = usersDb;
    }
    @Override
    public void execute(AddFriendInputData inputData) throws IOException {
        if (friendsList.findUserByName(inputData.getUser(), usersDb) != null){
            User user = friendsList.findUserByName(inputData.getUser(), usersDb);
            friendsList.addFriend(user);
            AddFriendOutputData addFriendOutputData = new AddFriendOutputData(user, friendsList);
            addFriendOutputBoundary.present(addFriendOutputData);
        }
    }
}
