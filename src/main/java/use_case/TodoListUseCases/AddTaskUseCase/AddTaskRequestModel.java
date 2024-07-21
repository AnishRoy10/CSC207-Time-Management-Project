package use_case.TodoListUseCases.AddTaskUseCase;

import java.time.LocalDateTime;

/**
 * Request model for adding a task.
 */
public class AddTaskRequestModel {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private String course;
    private String username;

    public AddTaskRequestModel(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course, String username) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.course = course;
        this.username = username;
    }

    // Getters and setters

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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
