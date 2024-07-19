package use_case.LeaderboardUseCases.update_score;

/**
 * Data required to update a score in the leaderboard.
 */
public class UpdateScoreInputData {
    private final String username;
    private final int score;

    /**
     * Constructs an UpdateScoreInputData object.
     *
     * @param username The username of the user.
     * @param score The new score to update.
     */
    public UpdateScoreInputData(String username, int score) {
        this.username = username;
        this.score = score;
    }

    /**
     * Gets the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the score.
     *
     * @return the score.
     */
    public int getScore() {
        return score;
    }
}
