package entity;

import java.time.LocalDate;

public class DailyLeaderboard extends Leaderboard {
    private LocalDate currentDate;

    public DailyLeaderboard(String name, LocalDate currentDate) {
        super(name);
        this.currentDate = currentDate;
    }

    @Override
    public void displayLeaderboard() {

    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }
}
