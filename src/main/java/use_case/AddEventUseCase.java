package use_case;

import entity.Calendar;
import entity.CalendarEvent;
import java.time.LocalDateTime;
import entity.Task;

/**
 * Use case for adding events to the calendar.
 */
public class AddEventUseCase implements AddEventInputBoundary {
    private final Calendar calendar;

    /**
     * Constructs an AddEventUseCase with the specified calendar.
     *
     * @param calendar The calendar to which events will be added.
     */
    public AddEventUseCase(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Adds a new event to the calendar.
     *
     * @param name        The name of the event.
     * @param description The description of the event.
     * @param startDate   The start date and time of the event.
     * @param endDate     The end date and time of the event.
     * @param priority    The priority level of the event.
     */
    @Override
    public void addEvent(String name, String description, LocalDateTime startDate, LocalDateTime endDate, String priority) {
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date and time cannot be null");
        }
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date and time must be after start date and time");
        }

        // Create a new event
        CalendarEvent event = new CalendarEvent(name, description, "Upcoming", priority, startDate, endDate);

        // Add the event to the calendar
        calendar.addEvent(event);
    }

    /**
     * Converts a Task to a CalendarEvent and adds it to the calendar.
     *
     * @param task The task to convert to a calendar event
     */
    public void convertTaskToEvent(Task task) {
        LocalDateTime eventStartDate = task.isCompleted() ? task.getCompletionDate() : task.getStartDate();
        LocalDateTime eventEndDate = task.isCompleted() ? task.getCompletionDate() : task.getDeadline();
        CalendarEvent event = new CalendarEvent(
                task.getTitle(),
                task.getDescription(),
                task.isCompleted() ? "Completed" : "Upcoming",
                "normal",
                eventStartDate,
                eventEndDate
        );
        calendar.addEvent(event);
    }
}