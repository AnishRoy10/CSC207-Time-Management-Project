package use_case.Leaderboard.add_score;

import java.util.Map;

public class AddScoreOutputData {
    private final Map<String, Integer> scores;

    public AddScoreOutputData(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}
