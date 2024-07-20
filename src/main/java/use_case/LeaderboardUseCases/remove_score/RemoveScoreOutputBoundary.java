package use_case.LeaderboardUseCases.remove_score;

/**
 * Output boundary interface for removing a score.
 */
public interface RemoveScoreOutputBoundary {
    /**
     * Presents the output data after removing a score.
     *
     * @param outputData Output data after removing the score.
     */
    void present(RemoveScoreOutputData outputData);

}
