package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;

/**
 * The Calendar class represents a user's calendar which contains multiple events.
 * It allows adding, removing, and retrieving events.
 */

public class Calendar {
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
            allEvents.addAll(eventList);
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
}
