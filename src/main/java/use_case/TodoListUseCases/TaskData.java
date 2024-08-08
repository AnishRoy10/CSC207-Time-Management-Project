package use_case.TodoListUseCases;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data model for task information.
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
