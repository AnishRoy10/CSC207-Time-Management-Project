package use_case.FriendsListUseCases.RemoveFriendUseCase;

import entity.User;

import java.io.IOException;

public interface RemoveFriendDataAccessInterface {
    User loadUser(String username) throws IOException;
    void writeUser(User user) throws IOException;
}
