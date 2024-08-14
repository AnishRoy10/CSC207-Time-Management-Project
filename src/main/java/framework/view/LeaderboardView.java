package framework.view;

import interface_adapter.controller.LeaderboardController;

import javax.swing.*;
import java.awt.*;

/**
 * User interface for managing and displaying leaderboards.
 */
public class LeaderboardView extends JPanel {
    private JPanel leaderboardPanel;
    private final LeaderboardController monthlyController;
    private final LeaderboardController allTimeController;
    private final LeaderboardController dailyController;

    public LeaderboardView(LeaderboardController monthlyController, LeaderboardController allTimeController, LeaderboardController dailyController) {
        this.monthlyController = monthlyController;
        this.allTimeController = allTimeController;
        this.dailyController = dailyController;

        initializeUI();
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());

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

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(leaderboardPanel), BorderLayout.CENTER);

        this.setSize(600, 400);
        this.setVisible(true);
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
}
