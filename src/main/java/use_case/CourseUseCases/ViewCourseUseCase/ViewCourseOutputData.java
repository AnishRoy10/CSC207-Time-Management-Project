package use_case.CourseUseCases.ViewCourseUseCase;

import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.MonthlyLeaderboard;
import entity.TodoList;

import javax.swing.*;

public class ViewCourseOutputData {
    private final boolean success;
    private final String message;

    private String courseDescription;
    private DefaultListModel<String> usernames;
    private TodoList todoList;
    private DailyLeaderboard dailyLeaderboard;
    private MonthlyLeaderboard monthlyLeaderboard;
    private AllTimeLeaderboard allTimeLeaderboard;

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
            DailyLeaderboard dailyLeaderboard,
            MonthlyLeaderboard monthlyLeaderboard,
            AllTimeLeaderboard allTimeLeaderboard) {
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

    public DailyLeaderboard getDailyLeaderboard() { return dailyLeaderboard; }

    public MonthlyLeaderboard getMonthlyLeaderboard() { return monthlyLeaderboard; }

    public AllTimeLeaderboard getAllTimeLeaderboard() { return allTimeLeaderboard; }
}
