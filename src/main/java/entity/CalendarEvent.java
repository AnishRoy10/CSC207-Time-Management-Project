package entity;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The CalendarEvent class represents an event in the calendar of the user.
 * Each event has a name, an optional description, its status (whether it has passed, is ongoing, or yet to start),
 * start date/time, end date/time (optional), duration (optional dependent on if end time is specified),
 * day of the week, and priority level.
 */

public class CalendarEvent {
    private String name;

    private String description;
    private boolean hasDescription;

    private String status;

    private String priorityLevel;
    private boolean hasPriorityLevel;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean hasEndDate;
    private ArrayList<Integer> duration;

    /**
     * Constructs a new calendar event with the specified parameters.
     *
     * @param name           The name of the event (required)
     * @param description    The description of the event (nullable)
     * @param status         The status of the event: must be either "Upcoming", "In Progress", or "Finished"
     * @param priorityLevel  The priority level of the event: must be either "Non-important, Major event"
     * @param startDate      The start date and time of the event (required)
     * @param endDate        The end date and time for the event (nullable and must be on the same day as startDate)
     */

    public CalendarEvent(String name, String description, String status, String priorityLevel,
                  LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;

        if (description != null) {
            this.description = description;
            this.hasDescription = true;
        } else {
            this.description = "No description specified for this Event";
            this.hasDescription = false;
        }

        this.status = status;

        if (priorityLevel != null) {
            this.priorityLevel = priorityLevel;
            this.hasPriorityLevel = true;
        } else {
            this.priorityLevel = "No priority level specified for this Event";
            this.hasPriorityLevel = false;
        }

        this.startDate = startDate;
        //Finds the difference in minutes between the dates before converting it into hours and minutes for the duration
        this.endDate = endDate;
        if (endDate != null) {
            int endHour = endDate.getHour();
            int endMinute = endHour * 60 + endDate.getMinute();
            int startHour = startDate.getHour();
            int startMinute = startHour + startDate.getMinute();
            int differenceInMinutes = (endMinute - startMinute);
            ArrayList<Integer> dur = new ArrayList<Integer>(2);
            dur.add(differenceInMinutes / 60);
            dur.add(differenceInMinutes % 60);
            this.duration = dur;
            this.hasEndDate = true;
        }
        else {
            this.hasEndDate = false;
            this.duration = null;
        }
    }
    // Getter method for the event name
    public String getName() {
        return this.name;
    }
    // Getter method for the event description
    public String getDescription() {
        return this.description;
    }
    // Getter method for the event status
    public String getStatus() {
        return this.status;
    }
    // Getter method for the event priority level
    public String getPriorityLevel() {
        return this.priorityLevel;
    }
    // Getter method for the event start date
    public LocalDateTime getStartDate() {
        return this.startDate;
    }
    // Getter method for the event end date
    public LocalDateTime getEndDate() {
        return this.endDate;
    }
    // Getter method for the event duration
    public ArrayList<Integer> getDuration() {
        return this.duration;
    }
    // Getter method for the event bool of having a non-null description
    public boolean getHasDescription() {
        return this.hasDescription;
    }
    // Getter method for the event bool of having a non-null priority level
    public boolean getHasPriorityLevel() {
        return this.hasPriorityLevel;
    }

    // Setter method for the name of an event
    public void setName(String name) {
        this.name = name;
    }

    // Setter method for the description of an event
    public void setDescription(String description) {
        this.description = description;
    }

    // Setter method for the

    // Setter method for the status of the event
    public void setStatus(String stat) {
        this.status = stat;
    }

    // Setter method for the priority level of an event
    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string representation of the event
     */
    @Override
    public String toString() {
        return name + ": " + description +
                ", Status: " + status +
                ", Priority Level: " + priorityLevel +
                ", Start Date: " + String(startDate) +
                ", End Date: " + (endDate != null ? endDate.toString() : "N/A");
    }

}
