package use_case.FriendsListUseCases.RefreshFriendsUseCase;

import java.io.IOException;

public interface RefreshFriendsInputBoundary {
    void execute(RefreshFriendInputData inputData) throws IOException;
}
