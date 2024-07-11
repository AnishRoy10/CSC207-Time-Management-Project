package use_case.Leaderboard;

import entity.Leaderboard;

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
     * @param username The username of the user.
     * @param score The score to add.
     */
    public void addScore(String username, int score) {
        leaderboard.addScore(username, score);
    }

    /**
     * Removes a score from the leaderboard.
     * @param username The username of the user.
     */
    public void removeScore(String username) {
        leaderboard.removeScore(username);
    }

    /**
     * Updates a score in the leaderboard.
     * @param username The username of the user.
     * @param score The new score to add to the current score.
     */
    public void updateScore(String username, int score) {
        leaderboard.updateScore(username, score);
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
