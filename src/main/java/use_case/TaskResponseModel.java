package use_case;

import entity.Task;

import java.time.LocalDateTime;

/**
 * A response model representing a task.
 */
public class TaskResponseModel {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private String course;
    private boolean completed;
    private LocalDateTime completionDate;

    /**
     * Constructs a TaskResponseModel from a Task entity.
     *
     * @param task The task entity
     */
    public TaskResponseModel(Task task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.startDate = task.getStartDate();
        this.deadline = task.getDeadline();
        this.course = task.getCourse();
        this.completed = task.isCompleted();
        this.completionDate = task.getCompletionDate();
    }

    // Getters and setters for all the fields
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }
}
