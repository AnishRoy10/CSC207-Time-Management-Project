package use_case.FriendsListUseCases.AddFriendUseCase;

import java.io.IOException;

public interface AddFriendInputBoundary {
    void execute(AddFriendInputData inputData) throws IOException;
}
