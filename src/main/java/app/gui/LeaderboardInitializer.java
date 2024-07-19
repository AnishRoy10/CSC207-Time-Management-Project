package app.gui;

import data_access.LeaderboardDataAccessObject;
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
import java.time.LocalDate;

/**
 * Initializer class for the leaderboard system.
 */
public class LeaderboardInitializer {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);

        Leaderboard monthlyLeaderboard = new MonthlyLeaderboard("Monthly Leaderboard", thisMonth);
        Leaderboard allTimeLeaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
        Leaderboard dailyLeaderboard = new DailyLeaderboard("Daily Leaderboard", today);

        LeaderboardDataAccessObject leaderboardDAO = new LeaderboardDataAccessObject();

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

        LeaderboardController monthlyController = new LeaderboardController(
                monthlyAddScoreUseCase,
                monthlyRemoveScoreUseCase,
                monthlyUpdateScoreUseCase,
                monthlyClearScoresUseCase,
                monthlyPresenter
        );

        LeaderboardController allTimeController = new LeaderboardController(
                allTimeAddScoreUseCase,
                allTimeRemoveScoreUseCase,
                allTimeUpdateScoreUseCase,
                allTimeClearScoresUseCase,
                allTimePresenter
        );

        LeaderboardController dailyController = new LeaderboardController(
                dailyAddScoreUseCase,
                dailyRemoveScoreUseCase,
                dailyUpdateScoreUseCase,
                dailyClearScoresUseCase,
                dailyPresenter
        );

        SwingUtilities.invokeLater(() -> new LeaderboardView(monthlyController, allTimeController, dailyController));
    }
}
