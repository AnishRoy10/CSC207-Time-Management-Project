package use_case.CalendarUseCases.ViewEventsUseCase;

import java.io.IOException;

/**
 * Interface for the view events interactor. Must have an execute method which takes as input an
 * instance of the ViewEventsInputData object
 */
public interface ViewEventsInputBoundary {
    void execute(ViewEventsInputData viewEventsInputData) throws IOException, ClassNotFoundException;
}
