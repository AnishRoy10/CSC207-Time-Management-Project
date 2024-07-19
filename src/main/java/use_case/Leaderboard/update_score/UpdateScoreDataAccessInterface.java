package use_case.Leaderboard.update_score;

/**
 * Data access interface for updating a score.
 */
public interface UpdateScoreDataAccessInterface {
    /**
     * Updates a score in the data store.
     *
     * @param username The username of the user.
     * @param score The new score.
     */
    void updateScore(String username, int score);
}
