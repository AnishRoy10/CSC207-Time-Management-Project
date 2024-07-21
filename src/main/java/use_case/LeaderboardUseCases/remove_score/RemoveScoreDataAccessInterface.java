package use_case.LeaderboardUseCases.remove_score;

/**
 * Data access interface for removing a score.
 */
public interface RemoveScoreDataAccessInterface {
    /**
     * Removes a score from the data store.
     *
     * @param username The username of the user.
     */
    void removeScore(String username);
}
