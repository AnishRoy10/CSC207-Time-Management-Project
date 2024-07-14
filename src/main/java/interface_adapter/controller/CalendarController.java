package interface_adapter.controller;

import use_case.ViewEventsUseCase;
import use_case.AddEventUseCase;
import entity.CalendarEvent;
import entity.Task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The CalendarController class acts as a mediator between the UI and the use cases for viewing and adding events.
 */
public class CalendarController {
    private final ViewEventsUseCase viewEventsUseCase;
    private final AddEventUseCase addEventUseCase;

    /**
     * Constructs a CalendarController with the specified use cases for viewing and adding events.
     *
     * @param viewEventsUseCase The use case for viewing events
     * @param addEventUseCase   The use case for adding events
     */
    public CalendarController(ViewEventsUseCase viewEventsUseCase, AddEventUseCase addEventUseCase) {
        this.viewEventsUseCase = viewEventsUseCase;
        this.addEventUseCase = addEventUseCase;
    }

    /**
     * Retrieves all events in the calendar.
     *
     * @return A list of all events
     */
    public List<CalendarEvent> getAllEvents() {
        return viewEventsUseCase.viewAllEvents();
    }

    /**
     * Retrieves events on a specific date.
     *
     * @param date The date for which to get the events
     * @return A list of events on the specified date
     */
    public List<CalendarEvent> getEventsOnDate(LocalDateTime date) {
        return viewEventsUseCase.viewEventsOnDate(date);
    }

    /**
     * Retrieves events within a specific date range.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return A list of events within the specified date range
     */
    public List<CalendarEvent> getEventsInRange(LocalDateTime startDate, LocalDateTime endDate) {
        return viewEventsUseCase.viewEventsInRange(startDate, endDate);
    }

    /**
     * Adds a new event to the calendar.
     *
     * @param name        The name of the event
     * @param description The description of the event
     * @param startDate   The start date and time of the event
     * @param endDate     The end date and time of the event
     * @param priority    The priority level of the event
     */
    public void addEvent(String name, String description, LocalDateTime startDate, LocalDateTime endDate, String priority) {
        addEventUseCase.addEvent(name, description, startDate, endDate, priority);
    }

    /**
     * Converts a task to an event and adds it to the calendar.
     *
     * @param task The task to convert to a calendar event
     */
    public void convertTaskToEvent(Task task) {
        addEventUseCase.convertTaskToEvent(task);
    }
}
