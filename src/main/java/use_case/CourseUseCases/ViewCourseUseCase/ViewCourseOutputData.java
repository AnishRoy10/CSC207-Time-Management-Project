package use_case.CourseUseCases.ViewCourseUseCase;

import entity.*;

import javax.swing.*;

/**
 * Outgoing data for the view course use case.
 */
public class ViewCourseOutputData {
    /// The success response given by the interactor.
    private final boolean success;
    /// The message response given by the interactor.
    private final String message;

    /// The description of the course to display.
    private String courseDescription;
    /// The list of users in the course to display.
    private DefaultListModel<String> usernames;
    /// The todolist of the course.
    private TodoList todoList;
    /// The daily leaderboard of the course.
    private Leaderboard dailyLeaderboard;
    /// The monthly leaderboard of the course.
    private Leaderboard monthlyLeaderboard;
    /// The all-time leaderboard of the course.
    private Leaderboard allTimeLeaderboard;

    /**
     * Construct a new failed output data object.
     * @param success The success response of the use case.
     * @param message The message response of the use case.
     */
    public ViewCourseOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Construct a new successful output data object.
     * @param success            The success response of the use case.
     * @param message            The message response of the use case.
     * @param courseDescription  The description of the course.
     * @param usernames          A list of users in the course.
     * @param todoList           The todolist of the course.
     * @param dailyLeaderboard   The courses' daily leaderboard.
     * @param monthlyLeaderboard The courses' monthly leaderboard.
     * @param allTimeLeaderboard The courses' all-time leaderboard.
     */
    public ViewCourseOutputData(
            boolean success,
            String message,
            String courseDescription,
            DefaultListModel<String> usernames,
            TodoList todoList,
            Leaderboard dailyLeaderboard,
            Leaderboard monthlyLeaderboard,
            Leaderboard allTimeLeaderboard) {
        this.success = success;
        this.message = message;
        this.courseDescription = courseDescription;
        this.usernames = usernames;
        this.todoList = todoList;
        this.dailyLeaderboard = dailyLeaderboard;
        this.monthlyLeaderboard = monthlyLeaderboard;
        this.allTimeLeaderboard = allTimeLeaderboard;
    }

    /**
     * Get the success response.
     * @return The response.
     */
    public boolean isSuccess() { return success; }

    /**
     * Get the message response.
     * @return The message.
     */
    public String getMessage() { return message; }

    /**
     * Get the course description.
     * @return The description.
     */
    public String getCourseDescription() { return courseDescription; }

    /**
     * Get the list of names as a list model.
     * @return A list model of usernames.
     */
    public DefaultListModel<String> getUsernames() { return usernames; }

    /**
     * Get the course todolist.
     * @return The todolist.
     */
    public TodoList getTodoList() { return todoList; }

    /**
     * Get the daily leaderboard.
     * @return The leaderboard.
     */
    public Leaderboard getDailyLeaderboard() { return dailyLeaderboard; }

    /**
     * Get the monthly leaderboard.
     * @return The leaderboard.
     */
    public Leaderboard getMonthlyLeaderboard() { return monthlyLeaderboard; }

    /**
     * Get the all-time leaderboard.
     * @return The leaderboard.
     */
    public Leaderboard getAllTimeLeaderboard() { return allTimeLeaderboard; }
}
