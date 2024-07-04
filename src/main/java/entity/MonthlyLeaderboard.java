package entity;

import java.time.LocalDate;

/* The MonthlyLeaderboard class represents a leaderboard specific to the current month.
 */

public class MonthlyLeaderboard extends Leaderboard {

    //Current Month
    private LocalDate month;

    public MonthlyLeaderboard(String name, LocalDate month) {
        super(name);
        // It is set to the first day of the month.
        this.month = month;
    }

    @Override
    public void displayLeaderboard() {


    }

    public LocalDate getCurrentMonth() {
        return month;
    }
}
