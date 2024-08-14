package use_case.CalendarUseCases.RemoveEventUseCase;

import entity.CalendarEvent;

import java.io.IOException;

/**
 * Interface telling the data access object the remove event interactor needs to have. Must have
 * a method that removes the inputted event from the user's calendar.
 */
public interface RemoveEventDataAccessInterface {
    void removeEvent(CalendarEvent event) throws IOException;
}
