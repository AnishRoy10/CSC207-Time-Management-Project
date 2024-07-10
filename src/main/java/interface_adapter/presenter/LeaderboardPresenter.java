package interface_adapter.presenter;

import entity.Leaderboard;

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
    public void displayLeaderboard() {
        leaderboard.displayLeaderboard();
    }
}