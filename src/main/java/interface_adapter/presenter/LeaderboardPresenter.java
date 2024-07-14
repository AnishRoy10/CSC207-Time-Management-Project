package interface_adapter.presenter;

import entity.Leaderboard;

import javax.swing.*;
import java.util.Map;

/**
 * LeaderboardPresenter presents the leaderboard data.
 */
public class LeaderboardPresenter {
    private final Leaderboard leaderboard;

    public LeaderboardPresenter(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    /**
     * Displays the leaderboard.
     */
    public void displayLeaderboard(JPanel panel) {
        int rank = 1;
        for (Map.Entry<String, Integer> entry : leaderboard.getScores().entrySet()) {
            panel.add(new JLabel(rank + ". " + entry.getKey() + ": " + entry.getValue()));
            rank++;
        }
        leaderboard.displayLeaderboard();
    }
}