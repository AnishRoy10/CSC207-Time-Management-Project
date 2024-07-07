package use_case;

import entity.Calendar;
import entity.CalendarEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for viewing events in the calendar.
 */
public class ViewEventsUseCase {
    private Calendar calendar;

    /**
     * Constructs a ViewEventsUseCase with the specified calendar.
     *
     * @param calendar The calendar whose events will be viewed
     */
    public ViewEventsUseCase(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Returns a list of all events in the calendar.
     *
     * @return A list of all events
     */
    public List<CalendarEvent> viewAllEvents() {
        return calendar.getAllEvents();
    }

    /**
     * Returns a list of events on a specific date.
     *
     * @param date The date for which to get the events
     * @return A list of events on the specified date
     */
    public List<CalendarEvent> viewEventsOnDate(LocalDateTime date) {
        return calendar.getEventsForDate(date);
    }

    /**
     * Returns a list of events within a specific date range.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return A list of events within the specified date range
     */
    public List<CalendarEvent> viewEventsInRange(LocalDateTime startDate, LocalDateTime endDate) {
        return calendar.getAllEvents().stream()
                .filter(event -> !event.getStartDate().isBefore(startDate) && !event.getEndDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
}
