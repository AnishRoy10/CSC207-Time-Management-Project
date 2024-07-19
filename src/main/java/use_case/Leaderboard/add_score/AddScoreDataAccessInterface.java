package use_case.Leaderboard.add_score;

/**
 * Data access interface for adding scores.
 */
public interface AddScoreDataAccessInterface {
    /**
     * Adds a score to the data store.
     *
     * @param username The username of the user.
     * @param score The score to add.
     */
    void addScore(String username, int score);
}
