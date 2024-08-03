package app.gui;

import data_access.FileCacheLeaderboardDataAccessObject;
import data_access.LeaderboardResetScheduler;
import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import framework.view.LeaderboardView;
import interface_adapter.controller.LeaderboardController;
import interface_adapter.presenter.LeaderboardPresenter;
import use_case.LeaderboardUseCases.add_score.*;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresInputBoundary;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresUseCase;
import use_case.LeaderboardUseCases.remove_score.*;
import use_case.LeaderboardUseCases.update_score.*;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Initializer class for the leaderboard system. This class sets up and initializes the leaderboard system, controllers,
 * views and reset scheduler.
 */
public class LeaderboardInitializer {
    /**
     * Initializes the leaderboard system.
     * This method sets up the leaderboard.
     */
    public static void LeaderboardInitializer() {
        LocalDate today = LocalDate.now();
        LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);

        try {
            FileCacheLeaderboardDataAccessObject leaderboardDAO = new FileCacheLeaderboardDataAccessObject("src/main/java/data_access/leaderboards.json");

            Map<String, Leaderboard> leaderboards = leaderboardDAO.readFromCache();

            // Initialize the scheduler to auto-reset leaderboards
            LeaderboardResetScheduler resetScheduler = new LeaderboardResetScheduler(leaderboards);
            resetScheduler.checkAndResetLeaderboards();


            Leaderboard monthlyLeaderboard = leaderboards.getOrDefault("monthly", new MonthlyLeaderboard("Monthly Leaderboard", thisMonth));
            Leaderboard allTimeLeaderboard = leaderboards.getOrDefault("allTime", new AllTimeLeaderboard("All-Time Leaderboard"));
            Leaderboard dailyLeaderboard = leaderboards.getOrDefault("daily", new DailyLeaderboard("Daily Leaderboard", today));

            LeaderboardPresenter monthlyPresenter = new LeaderboardPresenter(monthlyLeaderboard);
            LeaderboardPresenter allTimePresenter = new LeaderboardPresenter(allTimeLeaderboard);
            LeaderboardPresenter dailyPresenter = new LeaderboardPresenter(dailyLeaderboard);

            AddScoreInputBoundary monthlyAddScoreUseCase = new AddScoreUseCase(monthlyLeaderboard, monthlyPresenter, leaderboardDAO);
            AddScoreInputBoundary allTimeAddScoreUseCase = new AddScoreUseCase(allTimeLeaderboard, allTimePresenter, leaderboardDAO);
            AddScoreInputBoundary dailyAddScoreUseCase = new AddScoreUseCase(dailyLeaderboard, dailyPresenter, leaderboardDAO);

            RemoveScoreInputBoundary monthlyRemoveScoreUseCase = new RemoveScoreUseCase(monthlyLeaderboard, monthlyPresenter, leaderboardDAO);
            RemoveScoreInputBoundary allTimeRemoveScoreUseCase = new RemoveScoreUseCase(allTimeLeaderboard, allTimePresenter, leaderboardDAO);
            RemoveScoreInputBoundary dailyRemoveScoreUseCase = new RemoveScoreUseCase(dailyLeaderboard, dailyPresenter, leaderboardDAO);

            UpdateScoreInputBoundary monthlyUpdateScoreUseCase = new UpdateScoreUseCase(monthlyLeaderboard, monthlyPresenter, leaderboardDAO);
            UpdateScoreInputBoundary allTimeUpdateScoreUseCase = new UpdateScoreUseCase(allTimeLeaderboard, allTimePresenter, leaderboardDAO);
            UpdateScoreInputBoundary dailyUpdateScoreUseCase = new UpdateScoreUseCase(dailyLeaderboard, dailyPresenter, leaderboardDAO);

            ClearScoresInputBoundary monthlyClearScoresUseCase = new ClearScoresUseCase(monthlyLeaderboard, monthlyPresenter, leaderboardDAO);
            ClearScoresInputBoundary allTimeClearScoresUseCase = new ClearScoresUseCase(allTimeLeaderboard, allTimePresenter, leaderboardDAO);
            ClearScoresInputBoundary dailyClearScoresUseCase = new ClearScoresUseCase(dailyLeaderboard, dailyPresenter, leaderboardDAO);

            LeaderboardController monthlyController = new LeaderboardController(monthlyAddScoreUseCase, monthlyRemoveScoreUseCase, monthlyUpdateScoreUseCase, monthlyClearScoresUseCase, monthlyPresenter);
            LeaderboardController allTimeController = new LeaderboardController(allTimeAddScoreUseCase, allTimeRemoveScoreUseCase, allTimeUpdateScoreUseCase, allTimeClearScoresUseCase, allTimePresenter);
            LeaderboardController dailyController = new LeaderboardController(dailyAddScoreUseCase, dailyRemoveScoreUseCase, dailyUpdateScoreUseCase, dailyClearScoresUseCase, dailyPresenter);

            LeaderboardView leaderboardView = new LeaderboardView(monthlyController, allTimeController, dailyController);

            JFrame frame = new JFrame("Leaderboards");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(leaderboardView);
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
