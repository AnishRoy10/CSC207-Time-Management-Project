package use_case.LeaderboardUseCases.remove_score;

/**
 * Input boundary interface for removing a score.
 */
public interface RemoveScoreInputBoundary {
    /**
     * Removes a score from the leaderboard.
     *
     * @param inputData Data required to remove the score.
     * @return Output data after removing the score.
     */
    RemoveScoreOutputData removeScore(RemoveScoreInputData inputData);
}


