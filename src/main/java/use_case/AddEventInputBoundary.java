package use_case;

import java.time.LocalDateTime;

/**
 * Interface defining the input boundary for adding events to the calendar.
 */
public interface AddEventInputBoundary {
    /**
     * Adds a new event to the calendar.
     *
     * @param name        The name of the event.
     * @param description The description of the event.
     * @param startDate   The start date and time of the event.
     * @param endDate     The end date and time of the event.
     * @param priority    The priority level of the event.
     */
    void addEvent(String name, String description, LocalDateTime startDate, LocalDateTime endDate, String priority);
}
