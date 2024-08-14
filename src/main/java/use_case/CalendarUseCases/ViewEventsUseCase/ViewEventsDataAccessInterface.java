package use_case.CalendarUseCases.ViewEventsUseCase;

import entity.Calendar;
import java.io.IOException;

/**
 * Interface for the data access object used in the add event use case. Must have a getCalendar method
 * for retrieving the user's calendar
 */
public interface ViewEventsDataAccessInterface {
    public Calendar getCalendar() throws IOException, ClassNotFoundException;
}
