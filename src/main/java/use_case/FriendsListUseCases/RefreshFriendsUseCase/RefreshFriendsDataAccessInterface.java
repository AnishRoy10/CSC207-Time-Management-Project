package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import entity.User;

import java.io.IOException;

public interface RefreshFriendsDataAccessInterface {
    User loadUser(String username) throws IOException;
}
