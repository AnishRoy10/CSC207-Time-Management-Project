package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * The {@code Task} class represents a task in the to-do list. Each task has a unique identifier, a username,
 * a title, an optional description, a completion status, a start date, a deadline, and an associated course.
 * Tasks can be marked as completed, and points can be awarded upon completion.
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 3L; // Serial version UID for serialization compatibility

    private UUID id; // Unique identifier for the task
    private String username; // Username of the user to whom the task belongs
    private String title; // Title of the task (required)
    private String description; // Description of the task (optional)
    private boolean completed; // Indicates whether the task is completed or not
    private LocalDateTime startDate; // The start date and time of the task
    private LocalDateTime deadline; // The deadline date and time for the task
    private String course; // The course associated with the task (nullable)
    private LocalDateTime completionDate; // The completion date of the task
    private boolean pointsAwarded; // Indicates whether points have been awarded for the task

    /**
     * Constructs a new {@code Task} with the specified details.
     *
     * @param username    The username of the user to whom the task belongs.
     * @param title       The title of the task (required).
     * @param description The description of the task (optional).
     * @param startDate   The start date and time of the task.
     * @param deadline    The deadline date and time for the task.
     * @param course      The course associated with the task (nullable).
     */
    public Task(String username, String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        this.id = UUID.randomUUID(); // Generate a unique ID for the task
        this.username = username;
        this.title = title;
        this.description = description != null ? description : "";
        this.completed = false; // By default, a new task is not completed
        this.startDate = startDate;
        this.deadline = deadline;
        this.course = course;
        this.completionDate = null; // Completion date is null until the task is completed
        this.pointsAwarded = false; // By default, points are not awarded for a new task
    }

    /**
     * Gets the unique identifier of the task.
     *
     * @return The {@code UUID} of the task.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the task.
     *
     * @param id The {@code UUID} to set for the task.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the username of the user to whom the task belongs.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user to whom the task belongs.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the title of the task.
     *
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the completion status of the task.
     *
     * @return {@code true} if the task is completed, {@code false} otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed {@code true} to mark the task as completed, {@code false} otherwise.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the start date and time of the task.
     *
     * @return The start date and time of the task.
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date and time of the task.
     *
     * @param startDate The start date and time to set.
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the deadline date and time of the task.
     *
     * @return The deadline date and time of the task.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline date and time of the task.
     *
     * @param deadline The deadline date and time to set.
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the course associated with the task.
     *
     * @return The course associated with the task, or {@code null} if none is set.
     */
    public String getCourse() {
        return course;
    }

    /**
     * Sets the course associated with the task.
     *
     * @param course The course to associate with the task.
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Gets the completion date of the task.
     *
     * @return The completion date of the task, or {@code null} if the task is not completed.
     */
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion date of the task.
     *
     * @param completionDate The completion date to set.
     */
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Toggles the completion status of the task. If the task is marked as completed,
     * the completion date is set to the current date and time. If the task is marked
     * as incomplete, the completion date is cleared.
     */
    public void toggleTaskCompletion() {
        if (this.completed) {
            this.completed = false;
            this.completionDate = null;
        } else {
            this.completed = true;
            this.completionDate = LocalDateTime.now();
        }
    }

    /**
     * Marks the task as completed and sets the completion date to the current date and time.
     */
    public void completeTask() {
        this.completed = true;
        this.completionDate = LocalDateTime.now();
    }

    /**
     * Gets whether points have been awarded for completing the task.
     *
     * @return {@code true} if points have been awarded, {@code false} otherwise.
     */
    public boolean isPointsAwarded() {
        return pointsAwarded;
    }

    /**
     * Sets whether points have been awarded for completing the task.
     *
     * @param pointsAwarded {@code true} to mark that points have been awarded, {@code false} otherwise.
     */
    public void setPointsAwarded(boolean pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    /**
     * Returns a string representation of the task, including its ID, title, description, start date,
     * deadline, associated course, completion status, and completion date (if completed).
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return id + ": " + title + (description.isEmpty() ? "" : ": " + description) +
                " - Start: " + startDate +
                ", Deadline: " + deadline +
                ", Course: " + (course != null ? course : "None") +
                ", Completed: " + (completed ? "Yes" : "No") +
                (completed ? ", Completion Date: " + completionDate : "");
    }

    /**
     * Determines whether this task is equal to another object. Two tasks are considered equal if they have
     * the same ID, title, description, start date, deadline, and associated course.
     *
     * @param o The object to compare with.
     * @return {@code true} if this task is equal to the specified object, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(startDate, task.startDate) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(course, task.course);
    }

    /**
     * Returns the hash code for this task, based on its ID, title, description, start date, deadline, and course.
     *
     * @return The hash code for this task.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startDate, deadline, course);
    }
}
