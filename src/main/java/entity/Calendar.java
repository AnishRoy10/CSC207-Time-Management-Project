package entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.io.Serializable;

/**
 * The Calendar class representing a list of events and converting to do list items into
 * to-do-list items
 */

public class Calendar implements Serializable  {
    private static final long serialVersionUID = 4L; // Add serial version UID for serialization compatibility
    ArrayList<CalendarEvent> events;
    TodoList calendarToDoList;
    HashSet<ArrayList<CalendarEvent>> conflicts;

    public Calendar(ArrayList<CalendarEvent> events) {
        this.events = events;
    }

    // Returns a list of the events in the order that they start, the order staying the same
    // if the start dates are the same. Helper method for conflict checker.
    private ArrayList<CalendarEvent> orderEvents(CalendarEvent eventOne, CalendarEvent eventTwo) {
        boolean preconditionOne = eventOne.getStartDate().getHour() == eventTwo.getStartDate().getHour();
        boolean conditionOne = eventOne.getStartDate().getMinute() < eventTwo.getStartDate().getMinute();
        boolean conditionTwo = eventOne.getStartDate().getHour() < eventTwo.getStartDate().getHour();
        if ((preconditionOne && conditionOne) || conditionTwo) {
            ArrayList<CalendarEvent> orderedArray = new ArrayList<CalendarEvent>(2);
            orderedArray.add(eventOne);
            orderedArray.add(eventTwo);
            return orderedArray;
        } else {
            ArrayList<CalendarEvent> orderedArray = new ArrayList<CalendarEvent>(2);
            orderedArray.add(eventTwo);
            orderedArray.add(eventOne);
            return orderedArray;
        }
    }

    // Adds an event to the calendar.
    public void addEvent(CalendarEvent event) {
        events.add(event);
    }

    // Returns a list of all events in the calendar for a given date.
    public List<CalendarEvent> getEventsForDate(LocalDateTime date) {
        List<CalendarEvent> eventsOnDate = new ArrayList<>();
        for (CalendarEvent event : events) {
            if (event.getStartDate().toLocalDate().equals(date.toLocalDate())) {
                eventsOnDate.add(event);
            }
        }
        return eventsOnDate;
    }

    // Returns a list of all events in the calendar.
    public List<CalendarEvent> getAllEvents() {
        return new ArrayList<>(events);
    }
}