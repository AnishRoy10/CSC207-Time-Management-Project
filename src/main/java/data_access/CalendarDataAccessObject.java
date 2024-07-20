package data_access;

import use_case.ViewEventsUseCase.ViewEventsDataAccessInterface;
import entity.Calendar;
import java.io.IOException;

public class CalendarDataAccessObject implements ViewEventsDataAccessInterface{
    FileCacheUserDataAccessObject fileCacheUserDataAccessObject;

    public CalendarDataAccessObject() throws IOException {
        this.fileCacheUserDataAccessObject = new FileCacheUserDataAccessObject();
    }

    public Calendar getCalendar() throws IOException, ClassNotFoundException {
        return this.fileCacheUserDataAccessObject.ReadFromCache().getCalendar();
    }
}
