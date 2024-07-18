package use_case.Leaderboard.remove_score;

import java.util.Map;

public class RemoveScoreOutputData {
    private final Map<String, Integer> scores;

    public RemoveScoreOutputData(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}
