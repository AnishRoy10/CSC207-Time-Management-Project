package use_case.LeaderboardUseCases.add_score;

import java.util.Map;

/**
 * The output data after adding a score to the leaderboard.
 */
public class AddScoreOutputData {
    private final Map<String, Integer> scores;

    /**
     * Constructs an AddScoreOutputData object.
     * @param scores The scores on the leaderboard.
     */
    public AddScoreOutputData(Map<String, Integer> scores) {
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
