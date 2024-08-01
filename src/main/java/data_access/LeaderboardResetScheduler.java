package data_access;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.util.Map;

import entity.Leaderboard;

public class LeaderboardResetScheduler {
    private final Timer timer;
    private final FileCacheLeaderboardDataAccessObject leaderboardDAO;

    public LeaderboardResetScheduler(FileCacheLeaderboardDataAccessObject leaderboardDAO) {
        this.leaderboardDAO = leaderboardDAO;
        this.timer = new Timer(true);
        scheduleResets();
    }

    // Constructor for dependency injection, mainly for testing purposes
    protected LeaderboardResetScheduler(FileCacheLeaderboardDataAccessObject leaderboardDAO, Timer timer) {
        this.leaderboardDAO = leaderboardDAO;
        this.timer = timer;
        scheduleResets();
    }

    protected void scheduleResets() {
        scheduleDailyReset(getFirstDailyResetTime());
        scheduleMonthlyReset(getFirstMonthlyResetTime());
    }

    protected void scheduleDailyReset(Date firstDailyResetTime) {
        TimerTask dailyResetTask = new TimerTask() {
            @Override
            public void run() {
                resetDailyLeaderboard();
            }
        };
        timer.scheduleAtFixedRate(dailyResetTask, firstDailyResetTime, 24 * 60 * 60 * 1000); // Every 24 hours
    }

    protected void scheduleMonthlyReset(Date firstMonthlyResetTime) {
        TimerTask monthlyResetTask = new TimerTask() {
            @Override
            public void run() {
                resetMonthlyLeaderboard();
            }
        };
        timer.scheduleAtFixedRate(monthlyResetTask, firstMonthlyResetTime, 30L * 24 * 60 * 60 * 1000); // Every 30 days
    }

    protected void resetDailyLeaderboard() {
        try {
            Map<String, Leaderboard> leaderboards = leaderboardDAO.readFromCache();
            Leaderboard dailyLeaderboard = leaderboards.get("daily");
            if (dailyLeaderboard != null) {
                dailyLeaderboard.clearScores();
                leaderboardDAO.writeToCache(leaderboards);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void resetMonthlyLeaderboard() {
        try {
            Map<String, Leaderboard> leaderboards = leaderboardDAO.readFromCache();
            Leaderboard monthlyLeaderboard = leaderboards.get("monthly");
            if (monthlyLeaderboard != null) {
                monthlyLeaderboard.clearScores();
                leaderboardDAO.writeToCache(leaderboards);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Date getFirstDailyResetTime() {
        // Calculate the next midnight for the daily reset
        LocalDateTime nextMidnight = LocalDate.now().plusDays(1).atStartOfDay();
        return Date.from(nextMidnight.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getFirstMonthlyResetTime() {
        // Calculate the first day of the next month for the monthly reset
        LocalDateTime firstDayOfNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()).atStartOfDay();
        return Date.from(firstDayOfNextMonth.atZone(ZoneId.systemDefault()).toInstant());
    }
}
