package use_case.AddEventUseCase;

import entity.Calendar;
import entity.CalendarEvent;

import java.io.IOException;

public interface AddEventDataAccessInterface {
    Calendar getCalendar() throws IOException, ClassNotFoundException;
    void addEvent(CalendarEvent event) throws IOException, ClassNotFoundException;
}
