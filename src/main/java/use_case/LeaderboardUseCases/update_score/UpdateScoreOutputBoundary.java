package use_case.LeaderboardUseCases.update_score;

/**
 * Output boundary interface for updating a score.
 */
public interface UpdateScoreOutputBoundary {
    /**
     * Presents the output data after updating a score.
     *
     * @param outputData Output data after updating the score.
     */
    void present(UpdateScoreOutputData outputData);

}
