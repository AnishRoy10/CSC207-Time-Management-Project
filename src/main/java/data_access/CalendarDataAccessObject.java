package data_access;

import entity.CalendarEvent;
import entity.User;
import use_case.AddEventUseCase.AddEventDataAccessInterface;
import use_case.ViewEventsUseCase.ViewEventsDataAccessInterface;
import use_case.RemoveEventUseCase.RemoveEventDataAccessInterface;
import entity.Calendar;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CalendarDataAccessObject class is responsible for managing the caching of calendar use case related
 * classes to update the calendar of a user and retrieve information in the calendar of the user who is
 * logged in.
 */
public class CalendarDataAccessObject
        implements ViewEventsDataAccessInterface, AddEventDataAccessInterface, RemoveEventDataAccessInterface {

    private UserDAO userDAO;
    private String username;
    private User user;

    // Original constructor
    public CalendarDataAccessObject(String username, SQLDatabaseHelper dbHelper) throws IOException {
        this.username = username;
        this.userDAO = new UserDAO(dbHelper);
        this.user = userDAO.findByUsername(username);
    }

    // Method for getting the logged-in user's calendar
    public Calendar getCalendar() throws IOException, ClassNotFoundException {
        return this.user.getCalendar();
    }

    // Method for adding an event to the logged-in user's calendar
    public void addEvent(CalendarEvent event) throws IOException, ClassNotFoundException {
        this.user.addEvent(event);
        userDAO.WriteToCache(user);
    }

    // Method for removing an event from the logged-in user's calendar
    public void removeEvent(CalendarEvent event) throws IOException {
        this.user.removeEvent(event);
        userDAO.WriteToCache(user);
    }
}
