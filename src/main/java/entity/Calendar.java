package entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Calendar class representing a list of events and converting to do list items into
 * to-do-list items
 */

public class Calendar {
    ArrayList<CalendarEvent> events;
    TodoList calendarToDoList;
    HashSet<ArrayList<CalendarEvent>> conflicts;
    public Calendar(ArrayList<CalendarEvent> events, TodoList calendarToDoList) {
        this.events = events;
        this.calendarToDoList = calendarToDoList;
    }
    public ArrayList<CalendarEvent> conflictChecker(CalendarEvent eventOne, CalendarEvent eventTwo) {}
    public void addEvent(CalendarEvent event) {
        events.add(event);
    }


}
