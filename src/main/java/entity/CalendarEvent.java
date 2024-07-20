package entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

/**
 * The CalendarEvent class represents an event in the calendar of the user.
 * Each event has a name, an optional description, its status (whether it has passed, is ongoing, or yet to start),
 * start date/time, end date/time (optional), duration (optional dependent on if end time is specified),
 * day of the week, and priority level.
 */

public class CalendarEvent implements Serializable{
    private static final long serialVersionUID = 9L; // Add a serial version UID

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
     * @var status         The status of the event: must be either "Upcoming", "In Progress", or "Finished"
     * @param priorityLevel  The priority level of the event: must be either "Low", "Normal", or "High"
     * @param startDate      The start date and time of the event (required)
     * @param endDate        The end date and time for the event (nullable and must be on the same day as startDate)
     */

    public CalendarEvent(String name, String description, String priorityLevel,
                  LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;

        if (description != null) {
            this.description = description;
            this.hasDescription = true;
        } else {
            this.description = "No description specified for this Event";
            this.hasDescription = false;
        }

        this.priorityLevel = (priorityLevel != null ? priorityLevel : "Normal");

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

        if (LocalDateTime.now().isBefore(this.startDate)) {this.status = "Upcoming";}
        else if (LocalDateTime.now().isBefore(this.endDate)) {this.status = "In Progress";}
        else {this.status = "Finished";}
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
    // Getter method for the event bool of having an end date
    public boolean getHasEndDate() {
        return this.hasEndDate;
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

    // Setter method for the start date and time of the event
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    // Setter method for the end date and time of the event
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void updateStatus() {
        if (LocalDateTime.now().isBefore(this.startDate)) {this.status = "Upcoming";}
        else if (LocalDateTime.now().isBefore(this.endDate)) {this.status = "In Progress";}
        else {this.status = "Finished";}
    }

    public static List<CalendarEvent> eventsBetweenDates(LocalDateTime dateOne, LocalDateTime dateTwo, List<CalendarEvent> eventList) {
        List<CalendarEvent> includedEventList = new ArrayList<CalendarEvent>();
        for (CalendarEvent event : eventList) {
            boolean conditionOne = dateOne.isBefore(event.getStartDate());
            boolean conditionTwo =
                    (event.getHasEndDate() ? dateTwo.isAfter(event.getEndDate()) : dateTwo.isAfter(event.getStartDate()));
            if (conditionOne && conditionTwo){includedEventList.add(event);}
        }
        return includedEventList;
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
                ", Start Date: " + startDate.toString() +
                ", End Date: " + (endDate != null ? endDate.toString() : "N/A");
    }
}
