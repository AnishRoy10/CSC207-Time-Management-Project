package data_access;

import entity.CalendarEvent;
import entity.User;
import use_case.AddEventUseCase.AddEventDataAccessInterface;
import use_case.ViewEventsUseCase.ViewEventsDataAccessInterface;
import entity.Calendar;
import java.io.IOException;

public class CalendarDataAccessObject implements ViewEventsDataAccessInterface, AddEventDataAccessInterface
{
    private FileCacheUserDataAccessObject fileCacheUserDataAccessObject;
    private String username;
    private User user;
    public CalendarDataAccessObject(String username) throws IOException {
        this.username = username;
        this.fileCacheUserDataAccessObject =
                new FileCacheUserDataAccessObject("src/main/java/data_access/userCache.json");
        user = fileCacheUserDataAccessObject.findByUsername(username);
        System.out.println(fileCacheUserDataAccessObject.UserExists(username));
    }

    public Calendar getCalendar() throws IOException, ClassNotFoundException {
        return this.user.getCalendar();
    }
    public void addEvent(CalendarEvent event) throws IOException, ClassNotFoundException
    {this.user.addEvent(event);
    fileCacheUserDataAccessObject.WriteToCache(user);}
}
