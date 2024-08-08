package use_case.CalendarUseCases.ViewEventsUseCase;
import entity.CalendarEvent;
import entity.User;
import entity.Calendar;

import java.io.IOException;
import java.util.List;

public interface ViewEventsDataAccessInterface {
    public Calendar getCalendar() throws IOException, ClassNotFoundException;
}
