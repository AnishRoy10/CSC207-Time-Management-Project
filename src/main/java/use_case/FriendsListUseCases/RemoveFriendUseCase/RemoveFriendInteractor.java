package use_case.FriendsListUseCases.RemoveFriendUseCase;

import entity.FriendsList;
import entity.User;

import java.io.IOException;

public class RemoveFriendInteractor implements RemoveFriendInputBoundary{
    private final RemoveFriendOutputBoundary removeFriendOutputBoundary;
    private final RemoveFriendDataAccessInterface dao;
    private final User user;
    public RemoveFriendInteractor(RemoveFriendOutputBoundary removeFriendOutputBoundary, RemoveFriendDataAccessInterface dao, String username) throws IOException {
        this.removeFriendOutputBoundary = removeFriendOutputBoundary;
        this.dao = dao;
        this.user = dao.loadUser(username);
    }

    @Override
    public void execute(RemoveFriendInputData inputData) throws IOException, ClassNotFoundException {
        User removeUser = dao.loadUser(inputData.getFriend_name());
        if(user != null && removeUser != null){
            user.removeFriend(removeUser);
            RemoveFriendOutputData outputData = new RemoveFriendOutputData(user.getFriends());
            dao.writeUser(user);
            removeFriendOutputBoundary.present(outputData);
        }
    }
}
