package use_case.Leaderboard.clear_scores;

/**
 * Input boundary interface for clearing all the scores.
 */
public interface ClearScoresInputBoundary {
    /**
     * Clears the scores in the leaderboard.
     * @param inputData The data to clear the scores.
     * @return The data after all scores are cleared.
     */
    ClearScoresOutputData clearScores(ClearScoresInputData inputData);
}
