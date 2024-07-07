package use_case;

import entity.Leaderboard;
import entity.User;

/*
The LeaderboardService class
 */
public class LeaderboardUseCase {
    private final Leaderboard leaderboard;


    public LeaderboardUseCase(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public void addScore(User user, int score) {
        leaderboard.addScore(user, score);
    }

    public void removeScore(User user, int score) {
        leaderboard.removeScore(user);
    }

    public void updateScore(User user, int score) {
        leaderboard.updateScore(user, score);
    }

    public void clearScores() {
        leaderboard.clearScores();
    }

    public void displayLeaderboard() {
        leaderboard.displayLeaderboard();
    }


}
