package use_case.CalendarUseCases.ViewEventsUseCase;

import java.io.IOException;

public interface ViewEventsInputBoundary {
    void execute(ViewEventsInputData viewEventsInputData) throws IOException, ClassNotFoundException;
}
