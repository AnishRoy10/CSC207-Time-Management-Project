package use_case;

import java.time.LocalDateTime;

/**
 * Data model for task information.
 */
public class TaskData {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private boolean completed;
    private String course;
    private LocalDateTime completionDate;

    public TaskData(int id, String title, String description, LocalDateTime startDate, LocalDateTime deadline, boolean completed, String course, LocalDateTime completionDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.completed = completed;
        this.course = course;
        this.completionDate = completionDate;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
}
