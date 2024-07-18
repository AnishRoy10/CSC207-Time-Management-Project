package use_case.Leaderboard.update_score;

import java.util.Map;

public class UpdateScoreOutputData {
    private final Map<String, Integer> scores;

    public UpdateScoreOutputData(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}
