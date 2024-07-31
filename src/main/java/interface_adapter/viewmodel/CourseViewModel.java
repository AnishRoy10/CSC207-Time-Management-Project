package interface_adapter.viewmodel;

import entity.*;

import javax.swing.*;
import java.util.List;

/**
 * View model for the course view.
 */
public class CourseViewModel {
    private boolean success;
    private String message;

    private String courseDescription;
    private DefaultListModel<String> usernames;
    private TodoList todoList;
    private DailyLeaderboard dailyLeaderboard;
    private MonthlyLeaderboard monthlyLeaderboard;
    private AllTimeLeaderboard allTimeLeaderboard;

    public void setSuccess(boolean success) { this.success = success; }

    public void setMessage(String message) { this.message = message; }

    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }

    public void setUsernames(DefaultListModel<String> usernames) { this.usernames = usernames; }

    public void setTodoList(TodoList todoList) { this.todoList = todoList; }

    public void setDailyLeaderboard(DailyLeaderboard dailyLeaderboard) { this.dailyLeaderboard = dailyLeaderboard; }

    public void setMonthlyLeaderboard(MonthlyLeaderboard monthlyLeaderboard) { this.monthlyLeaderboard = monthlyLeaderboard; }

    public void setAllTimeLeaderboard(AllTimeLeaderboard allTimeLeaderboard) { this.allTimeLeaderboard = allTimeLeaderboard; }

    public boolean isSuccess() { return success; }

    public String getMessage() { return message; }

    public String getCourseDescription() { return courseDescription; }

    public DefaultListModel<String> getUsernames() { return usernames; }

    public TodoList getTodoList() { return todoList; }

    public DailyLeaderboard getDailyLeaderboard() { return dailyLeaderboard; }

    public MonthlyLeaderboard getMonthlyLeaderboard() { return monthlyLeaderboard; }

    public AllTimeLeaderboard getAllTimeLeaderboard() { return allTimeLeaderboard; }
}
