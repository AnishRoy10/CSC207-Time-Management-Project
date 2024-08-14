package use_case.CalendarUseCases.AddEventUseCase;

import java.io.IOException;

/**
 * Interface for addEventUseCaseInteractor. It must have an execute method which takes in
 * an instance of input data.
 */
public interface AddEventInputBoundary {
    void execute(AddEventInputData addEventInputData) throws IOException, ClassNotFoundException;
}
