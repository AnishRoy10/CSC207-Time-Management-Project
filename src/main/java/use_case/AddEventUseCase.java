package use_case;

import entity.Calendar;
import entity.CalendarEvent;
import entity.Task;

import java.time.LocalDateTime;

/**
 * Use case for adding an event to the calendar.
 */
public class AddEventUseCase {
    private Calendar calendar;

    /**
     * Constructs an AddEventUseCase with the specified calendar.
     *
     * @param calendar The calendar to which events will be added
     */
    public AddEventUseCase(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Adds an event to the calendar.
     *
     * @param name        The name of the event
     * @param description The description of the event (optional)
     * @param startDate   The start date and time of the event
     * @param endDate     The end date and time for the event
     */
    public void addEvent(String name, String description, LocalDateTime startDate, LocalDateTime endDate) {
        CalendarEvent event = new CalendarEvent(name, description, "Upcoming", "normal", startDate, endDate);
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
