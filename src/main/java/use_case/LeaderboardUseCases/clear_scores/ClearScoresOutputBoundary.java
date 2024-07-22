package use_case.LeaderboardUseCases.clear_scores;

/**
 * Output boundary interface for clearing scores.
 */
public interface ClearScoresOutputBoundary {
    /**
     * Presents the output data after clearing the scores.
     *
     * @param outputData Output data after clearing the scores.
     */
    void present(ClearScoresOutputData outputData);
}
