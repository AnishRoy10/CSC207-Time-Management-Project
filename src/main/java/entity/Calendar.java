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
    // Returns a list of the events in the order that they start, the order staying the same
    // if the start dates are the same. Helper method for conflict checker.
    private ArrayList<CalendarEvent> orderEvents(CalendarEvent eventOne, CalendarEvent eventTwo) {
        boolean preconditionOne = eventOne.getStartDate().getHour() = eventTwo.getStartDate().getHour();
        boolean conditionOne = eventOne.getStartDate().getMinute() < eventTwo.getStartDate().getMinute();
        boolean conditionTwo = eventOne.getStartDate().getHour() < eventTwo.getStartDate().getHour();
        if ((preconditionOne && conditionOne) || conditionTwo) {
            ArrayList<CalendarEvent> orderedArray = new ArrayList<CalendarEvent> (2);
            orderedArray.add(eventOne);
            orderedArray.add(eventTwo);
            return orderedArray;
        }
        else {
            ArrayList<CalendarEvent> orderedArray = new ArrayList<CalendarEvent> (2);
            orderedArray.add(eventTwo);
            orderedArray.add(eventOne);
            return orderedArray;
        }
    }
    public ArrayList<CalendarEvent> conflictChecker(CalendarEvent eventOne, CalendarEvent eventTwo) {}
    public void addEvent(CalendarEvent event) {
        events.add(event);
    }


}
