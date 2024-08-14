package use_case.CalendarUseCases.AddEventUseCase;

import entity.Calendar;
import entity.CalendarEvent;

import java.io.IOException;

/**
 * Specifies that the data access object the addEventUseCase needs must have a getCalendar
 * method which retrieves the user's calendar
 */
public interface AddEventDataAccessInterface {
    Calendar getCalendar() throws IOException, ClassNotFoundException;
    void addEvent(CalendarEvent event) throws IOException, ClassNotFoundException;
}
