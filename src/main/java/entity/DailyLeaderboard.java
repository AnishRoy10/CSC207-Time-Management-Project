package entity;

import java.time.LocalDate;

/**
 * Represents a daily leaderboard.
 */

public class DailyLeaderboard extends Leaderboard {
    private LocalDate currentDate;

    public DailyLeaderboard(String name, LocalDate currentDate) {
        super(name);
        this.currentDate = currentDate;
    }

    @Override
    public void displayLeaderboard() {
        System.out.println("Daily Leaderboard");
    }

    // Getter for local date
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    // Setter for date
    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }
}
