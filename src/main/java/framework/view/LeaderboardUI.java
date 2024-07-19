package framework.view;

import data_access.LeaderboardDataAccessObject;
import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import interface_adapter.controller.LeaderboardController;
import interface_adapter.presenter.LeaderboardPresenter;
import use_case.LeaderboardUseCases.add_score.*;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresInputBoundary;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresUseCase;
import use_case.LeaderboardUseCases.remove_score.*;
import use_case.LeaderboardUseCases.update_score.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * User interface for managing and displaying leaderboards.
 */

public class LeaderboardUI {
    private JPanel leaderboardPanel;
    private final LeaderboardController monthlyController;
    private final LeaderboardController allTimeController;
    private final LeaderboardController dailyController;

    public LeaderboardUI(LeaderboardController monthlyController, LeaderboardController allTimeController, LeaderboardController dailyController) {
        this.monthlyController = monthlyController;
        this.allTimeController = allTimeController;
        this.dailyController = dailyController;

        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Leaderboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));

        JButton monthlyButton = new JButton("Monthly Leaderboard");
        monthlyButton.addActionListener(e -> displayLeaderboard("Monthly"));
        JButton allTimeButton = new JButton("All-Time Leaderboard");
        allTimeButton.addActionListener(e -> displayLeaderboard("All-Time"));
        JButton dailyButton = new JButton("Daily Leaderboard");
        dailyButton.addActionListener(e -> displayLeaderboard("Daily"));

        buttonPanel.add(monthlyButton);
        buttonPanel.add(allTimeButton);
        buttonPanel.add(dailyButton);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(leaderboardPanel), BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void displayLeaderboard(String type) {
        leaderboardPanel.removeAll();
        JLabel titleLabel = new JLabel(type + " Leaderboard");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        leaderboardPanel.add(titleLabel);
        leaderboardPanel.add(Box.createVerticalStrut(10));
        switch (type) {
            case "Monthly":
                monthlyController.displayLeaderboard(leaderboardPanel);
                break;
            case "All-Time":
                allTimeController.displayLeaderboard(leaderboardPanel);
                break;
            case "Daily":
                dailyController.displayLeaderboard(leaderboardPanel);
                break;
            default:
                System.out.println("Invalid leaderboard type");
        }
        leaderboardPanel.revalidate();
        leaderboardPanel.repaint();
    }

    /**
     * Main method to run the application.
     *
     * @param args Command line arguments
     */
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

        SwingUtilities.invokeLater(() -> new LeaderboardUI(monthlyController, allTimeController, dailyController));
    }
}
