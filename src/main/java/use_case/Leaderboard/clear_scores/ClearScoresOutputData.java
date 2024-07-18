package use_case.Leaderboard.clear_scores;

import java.util.Map;

public class ClearScoresOutputData {
    private final Map<String, Integer> scores;

    public ClearScoresOutputData(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}
