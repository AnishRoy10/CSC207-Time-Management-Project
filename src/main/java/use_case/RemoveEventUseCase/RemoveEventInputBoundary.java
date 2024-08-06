package use_case.RemoveEventUseCase;

import java.io.IOException;

public interface RemoveEventInputBoundary {
    void execute(RemoveEventInputData removeEventInputData) throws IOException;
}
