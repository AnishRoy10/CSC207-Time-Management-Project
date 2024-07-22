package use_case.FriendsListUseCases.RemoveFriendUseCase;

import entity.FriendsList;
import entity.User;

import java.io.IOException;

/**
 * Use case interactor for removing a friend
 */
public class RemoveFriendInteractor implements RemoveFriendInputBoundary{
    private final RemoveFriendOutputBoundary removeFriendOutputBoundary;
    private final RemoveFriendDataAccessInterface dao;
    private final String username;
    public RemoveFriendInteractor(RemoveFriendOutputBoundary removeFriendOutputBoundary, RemoveFriendDataAccessInterface dao, String username) throws IOException {
        this.removeFriendOutputBoundary = removeFriendOutputBoundary;
        this.dao = dao;
        this.username = username;
    }

    /**
     * Removes a friend from the active users friends list
     * @param inputData
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void execute(RemoveFriendInputData inputData) throws IOException, ClassNotFoundException {
        User removeUser = dao.loadUser(inputData.getFriend_name());
        User user = dao.loadUser(username);
        if(user != null && removeUser != null){
            System.out.println(user.getFriends().exportFriendsNames());
            user.removeFriend(removeUser);
            System.out.println("Removed "+inputData.getFriend_name());
            System.out.println(user.getFriends().exportFriendsNames());
            RemoveFriendOutputData outputData = new RemoveFriendOutputData(user.getFriends());
            dao.writeUser(user);
            removeFriendOutputBoundary.present(outputData);
        }
    }
}
