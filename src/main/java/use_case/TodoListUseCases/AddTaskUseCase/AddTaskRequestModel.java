package use_case.TodoListUseCases.AddTaskUseCase;

import java.time.LocalDateTime;

/**
 * The {@code AddTaskRequestModel} class represents the data needed to create a new task in the to-do list.
 * It contains details such as the task's title, description, start date, deadline, course, and username.
 */
public class AddTaskRequestModel {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private String course;
    private String username;
    private String courseName;

    /**
     * Constructs a new {@code AddTaskRequestModel} with the specified details.
     *
     * @param title       The title of the task.
     * @param description The description of the task.
     * @param startDate   The start date and time of the task.
     * @param deadline    The deadline date and time for the task.
     * @param course      The course associated with the task.
     * @param username    The username of the user creating the task.
     */
    public AddTaskRequestModel(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course, String username) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.course = course;
        this.username = username;
        this.courseName = null; // default to null
    }

    /**
     * Constructs a new {@code AddTaskRequestModel} with the specified details, including the course name.
     *
     * @param title       The title of the task.
     * @param description The description of the task.
     * @param startDate   The start date and time of the task.
     * @param deadline    The deadline date and time for the task.
     * @param course      The course associated with the task.
     * @param username    The username of the user creating the task.
     * @param courseName  The name of the course associated with the task.
     */
    public AddTaskRequestModel(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course, String username, String courseName) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.course = course;
        this.username = username;
        this.courseName = courseName;
    }

    // Getters and setters

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
     * @param title The title to set.
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
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the start date and time of the task.
     *
     * @return The start date and time.
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
     * Returns the deadline date and time of the task.
     *
     * @return The deadline date and time.
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
     * Returns the course associated with the task.
     *
     * @return The course.
     */
    public String getCourse() {
        return course;
    }

    /**
     * Sets the course associated with the task.
     *
     * @param course The course to set.
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Returns the username of the user creating the task.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user creating the task.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the course name associated with the task, if any.
     *
     * @return The course name, or null if none is set.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name associated with the task.
     *
     * @param courseName The course name to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
