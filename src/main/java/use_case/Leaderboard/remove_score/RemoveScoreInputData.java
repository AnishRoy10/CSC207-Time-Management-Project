package use_case.Leaderboard.remove_score;

/**
 * Data required to remove a score from the leaderboard.
 */
public class RemoveScoreInputData {
    private final String username;

    /**
     * Constructs a RemoveScoreInputData object.
     *
     * @param username The username of the user whose score is to be removed.
     */
    public RemoveScoreInputData(String username) {
        this.username = username;
    }

    /**
     * Gets the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }
}
