package use_case.Leaderboard.add_score;


/**
 * Input boundary interface for adding a score.
 */
public interface AddScoreInputBoundary {
    /**
     * Adds a score to the leaderboard.
     * @param inputData The data to add the score.
     * @return Output data after the score is added.
     */
    AddScoreOutputData addScore(AddScoreInputData inputData);
}
