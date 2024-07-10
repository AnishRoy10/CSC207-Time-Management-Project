package use_case;

import entity.Leaderboard;
import entity.User;

/**
 * LeaderboardUseCase is used for manipulating the leaderboard.
 */

public class LeaderboardUseCase {
    private final Leaderboard leaderboard;


    public LeaderboardUseCase(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    /**
     * Adds a score to the leaderboard.
     * @param user The user.
     * @param score The score to add.
     */
    public void addScore(User user, int score) {
        leaderboard.addScore(user, score);
    }

    /**
     * Removes a score from the leaderboard.
     * @param user The user.
     */
    public void removeScore(User user) {
        leaderboard.removeScore(user);
    }

    /**
     * Updates a score in the leaderboard.
     * @param user The user.
     * @param score The new score to add to the current score.
     */
    public void updateScore(User user, int score) {
        leaderboard.updateScore(user, score);
    }

    /**
     * Clears all scores from the leaderboard.
     */
    public void clearScores() {
        leaderboard.clearScores();
    }

    /**
     * Displays the leaderboard.
     */
    public void displayLeaderboard() {
        leaderboard.displayLeaderboard();
    }


}
