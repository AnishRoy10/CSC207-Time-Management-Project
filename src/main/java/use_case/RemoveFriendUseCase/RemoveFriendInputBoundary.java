package use_case.RemoveFriendUseCase;

import use_case.RefreshFriendsUseCase.RefreshFriendInputData;

import java.io.IOException;

public interface RemoveFriendInputBoundary {
    void execute(RemoveFriendInputData inputData) throws IOException, ClassNotFoundException;
}
