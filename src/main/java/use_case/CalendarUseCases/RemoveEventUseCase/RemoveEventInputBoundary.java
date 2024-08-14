package use_case.CalendarUseCases.RemoveEventUseCase;

import java.io.IOException;

/**
 * interface for the remove event interactor. Must have an execute method that takes a remove
 * event input data instance as input.
 */
public interface RemoveEventInputBoundary {
    void execute(RemoveEventInputData removeEventInputData) throws IOException;
}
