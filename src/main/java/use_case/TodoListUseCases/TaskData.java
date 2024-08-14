package use_case.TodoListUseCases;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code TaskData} class represents a data model for task information.
 * It encapsulates all relevant details of a task, including its unique identifier,
 * the user it belongs to, its title, description, start and end dates,
 * completion status, associated course, and completion date.
 * This class is used primarily for transferring task data between layers in the application.
 */
public class TaskData {
    private UUID id;
    private String username;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private boolean completed;
    private String course;
    private LocalDateTime completionDate;

    /**
     * Constructs a new {@code TaskData} instance with the specified parameters.
     *
     * @param id              The unique identifier for the task.
     * @param username        The username of the user to whom the task belongs.
     * @param title           The title of the task.
     * @param description     The description of the task.
     * @param startDate       The start date and time of the task.
     * @param deadline        The deadline date and time for the task.
     * @param completed       Indicates whether the task is completed.
     * @param course          The course associated with the task, if any.
     * @param completionDate  The date and time when the task was completed, if applicable.
     */
    public TaskData(UUID id, String username, String title, String description, LocalDateTime startDate, LocalDateTime deadline, boolean completed, String course, LocalDateTime completionDate) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.completed = completed;
        this.course = course;
        this.completionDate = completionDate;
    }

    // Getters and setters

    /**
     * Returns the unique identifier of the task.
     *
     * @return The task's unique identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the task.
     *
     * @param id The UUID to set as the task's identifier.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the username of the user to whom the task belongs.
     *
     * @return The username associated with the task.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user to whom the task belongs.
     *
     * @param username The username to associate with the task.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the title of the task.
     *
     * @return The task's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title The title to set for the task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The description to set for the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the start date and time of the task.
     *
     * @return The task's start date and time.
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date and time of the task.
     *
     * @param startDate The start date and time to set for the task.
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the deadline date and time for the task.
     *
     * @return The task's deadline date and time.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline date and time for the task.
     *
     * @param deadline The deadline date and time to set for the task.
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return {@code true} if the task is completed, otherwise {@code false}.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed {@code true} to mark the task as completed, otherwise {@code false}.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Returns the course associated with the task.
     *
     * @return The course name associated with the task, or {@code null} if no course is associated.
     */
    public String getCourse() {
        return course;
    }

    /**
     * Sets the course associated with the task.
     *
     * @param course The course name to associate with the task.
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Returns the date and time when the task was completed, if applicable.
     *
     * @return The task's completion date and time, or {@code null} if the task is not completed.
     */
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the date and time when the task was completed.
     *
     * @param completionDate The date and time to set as the task's completion date.
     */
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
}
