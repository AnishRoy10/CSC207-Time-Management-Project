package use_case.CalendarUseCases.RemoveEventUseCase;

import java.io.IOException;

public interface RemoveEventInputBoundary {
    void execute(RemoveEventInputData removeEventInputData) throws IOException;
}
