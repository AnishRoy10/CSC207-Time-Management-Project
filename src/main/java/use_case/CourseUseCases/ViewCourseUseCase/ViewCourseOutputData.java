package use_case.CourseUseCases.ViewCourseUseCase;

import entity.*;

import javax.swing.*;

public class ViewCourseOutputData {
    private final boolean success;
    private final String message;

    private String courseDescription;
    private DefaultListModel<String> usernames;
    private TodoList todoList;
    private Leaderboard dailyLeaderboard;
    private Leaderboard monthlyLeaderboard;
    private Leaderboard allTimeLeaderboard;

    public ViewCourseOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

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

    public boolean isSuccess() { return success; }

    public String getMessage() { return message; }

    public String getCourseDescription() { return courseDescription; }

    public DefaultListModel<String> getUsernames() { return usernames; }

    public TodoList getTodoList() { return todoList; }

    public Leaderboard getDailyLeaderboard() { return dailyLeaderboard; }

    public Leaderboard getMonthlyLeaderboard() { return monthlyLeaderboard; }

    public Leaderboard getAllTimeLeaderboard() { return allTimeLeaderboard; }
}
