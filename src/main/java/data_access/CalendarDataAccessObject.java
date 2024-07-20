package data_access;

import entity.CalendarEvent;
import use_case.AddEventUseCase.AddEventDataAccessInterface;
import use_case.ViewEventsUseCase.ViewEventsDataAccessInterface;
import entity.Calendar;
import java.io.IOException;

public class CalendarDataAccessObject implements ViewEventsDataAccessInterface, AddEventDataAccessInterface
{
    FileCacheUserDataAccessObject fileCacheUserDataAccessObject;

    public CalendarDataAccessObject() throws IOException {
        this.fileCacheUserDataAccessObject = new FileCacheUserDataAccessObject();
    }

    public Calendar getCalendar() throws IOException, ClassNotFoundException {
        return this.fileCacheUserDataAccessObject.ReadFromCache().getCalendar();
    }
    public void addEvent(CalendarEvent event) throws IOException, ClassNotFoundException
    {this.fileCacheUserDataAccessObject.ReadFromCache().addEvent(event);}
}
