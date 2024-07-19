package use_case.Leaderboard.update_score;

/**
 * Input boundary interface for updating a score.
 */
public interface UpdateScoreInputBoundary {
    /**
     * Updates a score in the leaderboard.
     *
     * @param inputData Data required to update the score.
     * @return Output data after updating the score.
     */
    UpdateScoreOutputData updateScore(UpdateScoreInputData inputData);

}
