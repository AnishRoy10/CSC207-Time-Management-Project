package use_case.Leaderboard.update_score;

import java.util.Map;

/**
 * Output data after updating a score in the leaderboard.
 */
public class UpdateScoreOutputData {
    private final Map<String, Integer> scores;

    /**
     * Constructs an UpdateScoreOutputData object.
     *
     * @param scores The scores on the leaderboard after updating the score.
     */
    public UpdateScoreOutputData(Map<String, Integer> scores) {
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
