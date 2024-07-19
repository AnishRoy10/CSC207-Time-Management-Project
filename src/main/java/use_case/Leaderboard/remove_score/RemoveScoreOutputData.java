package use_case.Leaderboard.remove_score;

import java.util.Map;

/**
 * Output data after removing a score from the leaderboard.
 */
public class RemoveScoreOutputData {
    private final Map<String, Integer> scores;

    /**
     * Constructs a RemoveScoreOutputData object.
     *
     * @param scores The scores on the leaderboard after removing the score.
     */
    public RemoveScoreOutputData(Map<String, Integer> scores) {
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
