package use_case.FriendsListUseCases.AddFriendUseCase;

import entity.FriendsList;
import entity.User;

import java.io.IOException;

public class AddFriendInteractor implements AddFriendInputBoundary{
    private final AddFriendDataAccessInterface dao;
    private final FriendsList friendsList;
    private final User user;
    private final AddFriendOutputBoundary addFriendOutputBoundary;

    public AddFriendInteractor(AddFriendOutputBoundary addFriendOutputBoundary, AddFriendDataAccessInterface addFriendDataAccessInterface, String username) throws IOException {
        this.dao = addFriendDataAccessInterface;
        this.user = dao.loadUser(username);
        this.friendsList = user.getFriends();
        this.addFriendOutputBoundary = addFriendOutputBoundary;
    }
    @Override
    public void execute(AddFriendInputData inputData) throws IOException {
            User addUser = dao.loadUser(inputData.getUser());
            if (addUser != null) {
                friendsList.addFriend(addUser);
            }
            AddFriendOutputData addFriendOutputData = new AddFriendOutputData(friendsList);
            dao.writeUser(user);
            addFriendOutputBoundary.present(addFriendOutputData);
        }
    }

