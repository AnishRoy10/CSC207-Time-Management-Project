package use_case.FriendsListUseCases.AddFriendUseCase;

import entity.FriendsList;
import entity.User;

import java.io.IOException;

/**
 * Use case interactor for adding a friend
 */
public class AddFriendInteractor implements AddFriendInputBoundary{
    private final AddFriendDataAccessInterface dao;
    private final String username;
    private final AddFriendOutputBoundary addFriendOutputBoundary;

    public AddFriendInteractor(AddFriendOutputBoundary addFriendOutputBoundary, AddFriendDataAccessInterface addFriendDataAccessInterface, String username) throws IOException {
        this.dao = addFriendDataAccessInterface;
        this.username = username;
        this.addFriendOutputBoundary = addFriendOutputBoundary;
    }

    /**
     * Adds a friend to the active users friendslist
     * @param inputData
     * @throws IOException
     */
    @Override
    public void execute(AddFriendInputData inputData) throws IOException {
            User addUser = dao.loadUser(inputData.getUser());
            User user = dao.loadUser(username);
            FriendsList friendsList = user.getFriends();
            if (addUser != null) {
                user.addFriend(addUser);
                dao.writeUser(user);
                System.out.println("Added friend " + addUser.getUsername());
            }
            AddFriendOutputData addFriendOutputData = new AddFriendOutputData(friendsList);
            addFriendOutputBoundary.present(addFriendOutputData);
        }
    }

