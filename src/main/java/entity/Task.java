package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Task class represents a task in the to-do list.
 * Each task has a title, an optional description, completion status, start date, deadline, and an associated course.
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serial version UID

    private static int idCounter = 0; // Static counter to generate unique IDs

    private int id; // Unique identifier for the task
    private String title; // Title of the task (required)
    private String description; // Description of the task (optional)
    private boolean completed; // Indicates whether the task is completed or not
    private LocalDateTime startDate; // The start date and time of the task
    private LocalDateTime deadline; // The deadline date and time for the task
    private String course; // The course associated with the task (nullable)
    private LocalDateTime completionDate; // The completion date of the task

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
        this.id = ++idCounter; // Increment the counter and assign it as the ID
        this.title = title;
        this.description = description != null ? description : "";
        this.completed = false; // By default, a new task is not completed
        this.startDate = startDate;
        this.deadline = deadline;
        this.course = course;
        this.completionDate = null;
    }

    // Getter for the ID
    public int getId() {
        return id;
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
     * Toggles the completion status of the task.
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
        return id + ": " + title + (description.isEmpty() ? "" : ": " + description) +
                " - Start: " + startDate +
                ", Deadline: " + deadline +
                ", Course: " + (course != null ? course : "None") +
                ", Completed: " + (completed ? "Yes" : "No") +
                (completed ? ", Completion Date: " + completionDate : "");
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startDate, deadline, course);
    }

    public void setId(int id) {
        this.id = id;
    }
}
