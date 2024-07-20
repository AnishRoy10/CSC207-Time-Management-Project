package use_case.AddFriendUseCase;

import java.io.IOException;

public interface AddFriendInputBoundary {
    void execute(AddFriendInputData inputData) throws IOException;
}
