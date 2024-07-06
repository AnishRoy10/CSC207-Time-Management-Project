package entity;

import java.time.LocalDateTime;

/**
 * The Task class represents a task in the to-do list.
 * Each task has a title, an optional description, completion status, start date, deadline, and an associated course.
 */
public class Task {
    // Title of the task (required)
    private String title;

    // Description of the task (optional)
    private String description;

    // Indicates whether the task is completed or not
    private boolean completed;

    // The start date and time of the task
    private LocalDateTime startDate;

    // The deadline date and time for the task
    private LocalDateTime deadline;

    // The course associated with the task (nullable)
    private String course;

    // The completion date of the task
    private LocalDateTime completionDate;

    /**
     * Constructs a new Task with the specified details.
     *
     * @param title       The title of the task (required)
     * @param description The description of the task (optional)
     * @param startDate   The start date and time of the task
     * @param deadline    The deadline date and time for the task
     * @param course      The course associated with the task (nullable)
     */
    public Task(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        this.title = title;
        this.description = description != null ? description : "";
        this.completed = false; // By default, a new task is not completed
        this.startDate = startDate;
        this.deadline = deadline;
        this.course = course;
        this.completionDate = null;
    }

    // Getter for the title
    public String getTitle() {
        return title;
    }

    // Setter for the title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }

    // Setter for the description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for the completed status
    public boolean isCompleted() {
        return completed;
    }

    // Setter for the completed status
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Getter for the start date
    public LocalDateTime getStartDate() {
        return startDate;
    }

    // Setter for the start date
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    // Getter for the deadline
    public LocalDateTime getDeadline() {
        return deadline;
    }

    // Setter for the deadline
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    // Getter for the course
    public String getCourse() {
        return course;
    }

    // Setter for the course
    public void setCourse(String course) {
        this.course = course;
    }

    // Getter for the completion date
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    // Setter for the completion date
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Marks the task as completed and sets the completion date.
     */
    public void completeTask() {
        this.completed = true;
        this.completionDate = LocalDateTime.now();
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string representation of the task
     */
    @Override
    public String toString() {
        return title + (description.isEmpty() ? "" : ": " + description) +
                " - Start: " + startDate +
                ", Deadline: " + deadline +
                ", Course: " + (course != null ? course : "None") +
                ", Completed: " + (completed ? "Yes" : "No") +
                (completed ? ", Completion Date: " + completionDate : "");
    }
}
