package use_case.Leaderboard.add_score;

/**
 * THe data required to add a score to the leaderboard.
 */
public class AddScoreInputData {
    private final String username;
    private final int score;

    /**
     * Constructs an AddScoreInputData object.
     *
     * @param username The username of the user.
     * @param score The score to add.
     */
    public AddScoreInputData(String username, int score) {
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
