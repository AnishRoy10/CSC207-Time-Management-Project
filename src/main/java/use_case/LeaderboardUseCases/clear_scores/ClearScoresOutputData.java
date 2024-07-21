package use_case.LeaderboardUseCases.clear_scores;

import java.util.Map;

/**
 * Output data after clearing scores from the leaderboard.
 */
public class ClearScoresOutputData {

    private final Map<String, Integer> scores;

    /**
     * Constructs a ClearScoresOutputData object.
     *
     * @param scores The scores on the leaderboard after clearing.
     */
    public ClearScoresOutputData(Map<String, Integer> scores) {
        this.scores = scores;
    }

    /**
     * Gets the scores.
     *
     * @return the scores.
     */
    public Map<String, Integer> getScores() {
        return scores;
    }
}
