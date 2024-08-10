package use_case.CalendarUseCases.RemoveEventUseCase;

import entity.CalendarEvent;

import java.io.IOException;

public interface RemoveEventDataAccessInterface {
    void removeEvent(CalendarEvent event) throws IOException;
}
