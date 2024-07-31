package interface_adapter.viewmodel;

import entity.AllTimeLeaderboard;
import entity.Course;
import entity.DailyLeaderboard;
import entity.MonthlyLeaderboard;

import javax.swing.*;
import java.util.List;

/**
 * View model for the course view.
 */
public class CourseViewModel {
    private boolean successful;
    private String courseDescription;
    private DefaultListModel<String> usernames;
    private DailyLeaderboard dailyLeaderboard;
    private MonthlyLeaderboard monthlyLeaderboard;
    private AllTimeLeaderboard allTimeLeaderboard;

    public void setSuccessful(boolean successful) { this.successful = successful; }

    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }

    public void setUsernames(DefaultListModel<String> usernames) { this.usernames = usernames; }

    public void setDailyLeaderboard(DailyLeaderboard dailyLeaderboard) { this.dailyLeaderboard = dailyLeaderboard; }

    public void setMonthlyLeaderboard(MonthlyLeaderboard monthlyLeaderboard) { this.monthlyLeaderboard = monthlyLeaderboard; }

    public void setAllTimeLeaderboard(AllTimeLeaderboard allTimeLeaderboard) { this.allTimeLeaderboard = allTimeLeaderboard; }

    public boolean wasSuccessful() { return successful; }

    public String getCourseDescription() { return courseDescription; }

    public DefaultListModel<String> getUsernames() { return usernames; }

    public DailyLeaderboard getDailyLeaderboard() { return dailyLeaderboard; }

    public MonthlyLeaderboard getMonthlyLeaderboard() { return monthlyLeaderboard; }

    public AllTimeLeaderboard getAllTimeLeaderboard() { return allTimeLeaderboard; }
}
