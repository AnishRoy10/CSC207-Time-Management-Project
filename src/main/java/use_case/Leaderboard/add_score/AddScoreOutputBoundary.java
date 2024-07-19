package use_case.Leaderboard.add_score;

/**
 * Output boundary interface for adding a score.
 */
public interface AddScoreOutputBoundary {
    /**
     * Presents the output data after adding a score.
     * @param outputData The data after adding a score.
     */
    void present(AddScoreOutputData outputData);

}
