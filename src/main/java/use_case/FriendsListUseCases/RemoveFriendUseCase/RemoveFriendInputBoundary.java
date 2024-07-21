package use_case.FriendsListUseCases.RemoveFriendUseCase;

import java.io.IOException;

public interface RemoveFriendInputBoundary {
    void execute(RemoveFriendInputData inputData) throws IOException, ClassNotFoundException;
}
