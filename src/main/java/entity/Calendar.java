package entity;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import entity.CalendarEvent;
import java.io.Serializable;
/**
 * The Calendar class represents a user's calendar which contains multiple events.
 * It allows adding, removing, and retrieving events.
 */

public class Calendar implements Serializable{
    private static final long serialVersionUID = 1124412L; // Add a serial version UID
    /**
     * A map that holds the events in the calendar, with the key being the date of the event.
     */
    private HashMap<LocalDateTime, List<CalendarEvent>> events;

    /**
     * Constructs a new Calendar.
     */
    public Calendar() {
        events = new HashMap<>();
    }

    /**
     * Adds an event to the calendar.
     *
     * @param event The event to be added.
     */
    public void addEvent(CalendarEvent event) {
        LocalDateTime startDate = event.getStartDate();
        if (!events.containsKey(startDate)) {
            events.put(startDate, new ArrayList<>());
        }
        events.get(startDate).add(event);
    }

    /**
     * Removes an event from the calendar.
     *
     * @param event The event to be removed.
     */
    public void removeEvent(CalendarEvent event) {
        LocalDateTime startDate = event.getStartDate();
        if (events.containsKey(startDate)) {
            events.get(startDate).remove(event);
            if (events.get(startDate).isEmpty()) {
                events.remove(startDate);
            }
        }
    }

    /**
     * Gets the list of events for a specific date.
     *
     * @param date The date for which to get the events.
     * @return The list of events on the specified date.
     */
    public List<CalendarEvent> getEventsForDate(LocalDateTime date) {
        return events.getOrDefault(date, new ArrayList<>());
    }

    /**
     * Gets all the events in the calendar.
     *
     * @return A list of all events in the calendar.
     */
    public List<CalendarEvent> getAllEvents() {
        List<CalendarEvent> allEvents = new ArrayList<>();
        for (List<CalendarEvent> eventList : events.values()) {
            for (CalendarEvent event : eventList)
            allEvents.add(event);
        }
        return allEvents;
    }

    /**
     * Checks if there are any events on a specific date.
     *
     * @param date The date to check for events.
     * @return True if there are events on the specified date, false otherwise.
     */
    public boolean hasEventsOnDate(LocalDateTime date) {
        return events.containsKey(date);
    }

    /**
     * Returns a list of events in the calendar which start and end
     * between two specific dates.
     * @param dateOne The earlier specific date.
     * @param dateTwo The later specific date.
     * @return the list of events in the calendar in between the specific dates.
     */
    public List<CalendarEvent> eventsBetweenDates(LocalDateTime dateOne, LocalDateTime dateTwo) {
        List<CalendarEvent> eventList = this.getAllEvents();
        List<CalendarEvent> includedEventList = new ArrayList<>();
        for (CalendarEvent event : eventList) {
            boolean conditionOne = dateOne.isBefore(event.getStartDate());
            boolean conditionTwo =
                    (event.getHasEndDate() ? dateTwo.isAfter(event.getEndDate()) : dateTwo.isAfter(event.getStartDate()));
            if (conditionOne && conditionTwo){includedEventList.add(event);}
        }
        return includedEventList;
    }

    /**
     * Checks if there are any events in the calendar which start and end
     * between two specific dates.
     * @param dateOne The earlier specific date.
     * @param dateTwo The later specific date.
     * @return True if there are events in the calendar in between the specific dates.
     */
    public boolean hasEventsBetweenDates(LocalDateTime dateOne, LocalDateTime dateTwo) {
        List<CalendarEvent> eventsInBetween = this.eventsBetweenDates(dateOne, dateTwo);
        if (eventsInBetween.isEmpty()) {return false;}
        else {return true;}
    }

    /**
     * Returns a string representation of the calendar.
     *
     * @return A string representation of the calendar.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LocalDateTime date : events.keySet()) {
            sb.append("Date: ").append(date).append("\n");
            for (CalendarEvent event : events.get(date)) {
                sb.append(event).append("\n");
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.of(2024, Month.JULY, 13, 21, 30);
        LocalDateTime end = LocalDateTime.of(2024, Month.JULY, 13, 21, 40);
        CalendarEvent eventer = new CalendarEvent("Awesome Saucer", "Bad Description",
                "High", start, end);
        Calendar calendar = new Calendar();
        calendar.addEvent(eventer);
        LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 13, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 13, 23, 59);
        boolean toBePrinted = calendar.hasEventsBetweenDates(startDate, endDate);
        System.out.println(toBePrinted);
    }
}