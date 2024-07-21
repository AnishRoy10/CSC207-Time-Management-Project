package data_access;

import entity.CalendarEvent;
import entity.User;
import use_case.AddEventUseCase.AddEventDataAccessInterface;
import use_case.ViewEventsUseCase.ViewEventsDataAccessInterface;
import entity.Calendar;
import java.io.IOException;
/**
 * The CalendarDataAccessObject class is responsible for managing the caching of calendar use case related
 * classes to update the calendar of a user and retrieve information in the calendar of the user who is
 * logged in.
 */
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
    }
    // Method for getting the logged in user's calendar
    public Calendar getCalendar() throws IOException, ClassNotFoundException {
        return this.user.getCalendar();
    }
    // Method for adding an event to the logged in user's calendar.
    public void addEvent(CalendarEvent event) throws IOException, ClassNotFoundException
    {this.user.addEvent(event);
    fileCacheUserDataAccessObject.WriteToCache(user);}
}
