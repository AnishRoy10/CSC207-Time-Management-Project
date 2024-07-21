package use_case.FriendsListUseCases.AddFriendUseCase;

import entity.User;

import java.io.IOException;

public interface AddFriendDataAccessInterface {
    void writeUser(User user) throws IOException;
    User loadUser(String username) throws IOException;
}
