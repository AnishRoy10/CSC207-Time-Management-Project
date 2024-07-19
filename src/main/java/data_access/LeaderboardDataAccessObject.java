package data_access;

import use_case.Leaderboard.add_score.AddScoreDataAccessInterface;
import use_case.Leaderboard.clear_scores.ClearScoresDataAccessInterface;
import use_case.Leaderboard.remove_score.RemoveScoreDataAccessInterface;
import use_case.Leaderboard.update_score.UpdateScoreDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the leaderboard data access interface.
 */
public class LeaderboardDataAccessObject implements AddScoreDataAccessInterface, ClearScoresDataAccessInterface, RemoveScoreDataAccessInterface, UpdateScoreDataAccessInterface {
    private final Map<String, Integer> scores = new HashMap<>();

    @Override
    public void addScore(String username, int score) {
        scores.put(username, score);
    }

    @Override
    public void clearScores() {
        scores.clear();
    }

    @Override
    public void removeScore(String username) {
        scores.remove(username);
    }

    @Override
    public void updateScore(String username, int score) {
        scores.put(username, score);
    }

    public Map<String, Integer> getScores() {
        return new HashMap<>(scores);
    }
}
