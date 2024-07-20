package use_case.AddEventUseCase;

import java.io.IOException;

public interface AddEventInputBoundary {
    void execute(AddEventInputData addEventInputData) throws IOException, ClassNotFoundException;
}
