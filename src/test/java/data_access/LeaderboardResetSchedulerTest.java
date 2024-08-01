package data_access;

import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LeaderboardResetSchedulerTest {
    private FileCacheLeaderboardDataAccessObject leaderboardDAO;
    private Timer timer;
    private LeaderboardResetScheduler scheduler;

    @BeforeEach
    void setUp() {
        leaderboardDAO = mock(FileCacheLeaderboardDataAccessObject.class);
        timer = mock(Timer.class);
        scheduler = new LeaderboardResetScheduler(leaderboardDAO, timer);
    }

    @AfterEach
    void tearDown() {
        // Delete the schedule file after each test
        File scheduleFile = new File("reset_schedule.dat");
        if (scheduleFile.exists()) {
            scheduleFile.delete();
        }
    }

    @Test
    void testDailyReset() throws IOException {
        Leaderboard dailyLeaderboard = new DailyLeaderboard("Daily Leaderboard", LocalDate.now());
        dailyLeaderboard.addScore("testUser", 1000);

        Map<String, Leaderboard> leaderboards = new HashMap<>();
        leaderboards.put("daily", dailyLeaderboard);

        when(leaderboardDAO.readFromCache()).thenReturn(leaderboards);

        // Capture the TimerTask that was scheduled
        ArgumentCaptor<TimerTask> captor = ArgumentCaptor.forClass(TimerTask.class);
        doNothing().when(timer).scheduleAtFixedRate(captor.capture(), any(Date.class), anyLong());

        // Schedule the reset and capture the TimerTask
        scheduler.scheduleDailyReset(new Date());

        // Run the captured TimerTask to simulate the timer triggering
        captor.getValue().run();

        verify(leaderboardDAO, times(1)).writeToCache(leaderboards);
        assertTrue(dailyLeaderboard.getScores().isEmpty(), "Daily leaderboard should be empty after reset.");
    }

    @Test
    void testMonthlyReset() throws IOException {
        Leaderboard monthlyLeaderboard = new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now().withDayOfMonth(1));
        monthlyLeaderboard.addScore("testUser", 500);

        Map<String, Leaderboard> leaderboards = new HashMap<>();
        leaderboards.put("monthly", monthlyLeaderboard);

        when(leaderboardDAO.readFromCache()).thenReturn(leaderboards);

        // Capture the TimerTask that was scheduled
        ArgumentCaptor<TimerTask> captor = ArgumentCaptor.forClass(TimerTask.class);
        doNothing().when(timer).scheduleAtFixedRate(captor.capture(), any(Date.class), anyLong());

        // Schedule the reset and capture the TimerTask
        scheduler.scheduleMonthlyReset(new Date());

        // Run the captured TimerTask to simulate the timer triggering
        captor.getValue().run();

        verify(leaderboardDAO, times(1)).writeToCache(leaderboards);
        assertTrue(monthlyLeaderboard.getScores().isEmpty(), "Monthly leaderboard should be empty after reset.");
    }
}
