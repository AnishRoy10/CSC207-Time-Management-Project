package interface_adapter.presenter;

import entity.Leaderboard;
import use_case.LeaderboardUseCases.add_score.AddScoreOutputBoundary;
import use_case.LeaderboardUseCases.add_score.AddScoreOutputData;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresOutputData;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreOutputBoundary;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreOutputData;
import use_case.LeaderboardUseCases.update_score.UpdateScoreOutputBoundary;
import use_case.LeaderboardUseCases.update_score.UpdateScoreOutputData;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresOutputBoundary;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LeaderboardPresenter presents the leaderboard data.
 */
public class LeaderboardPresenter implements
        AddScoreOutputBoundary,
        RemoveScoreOutputBoundary,
        UpdateScoreOutputBoundary,
        ClearScoresOutputBoundary {
    private final Leaderboard leaderboard;

    public LeaderboardPresenter(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    /**
     * Displays the leaderboard.
     */
    public void displayLeaderboard(JPanel panel) {
        List<Map.Entry<String, Integer>> sortedScores = leaderboard.getScores().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedScores) {
            panel.add(new JLabel(rank + ". " + entry.getKey() + ": " + entry.getValue()));
            rank++;
        }
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void present(AddScoreOutputData outputData) {
        // Handle output data for adding a score
        displayScores(outputData.getScores());
    }

    @Override
    public void present(RemoveScoreOutputData outputData) {
        // Handle output data for removing a score
        displayScores(outputData.getScores());
    }

    @Override
    public void present(UpdateScoreOutputData outputData) {
        // Handle output data for updating a score
        displayScores(outputData.getScores());
    }

    @Override
    public void present(ClearScoresOutputData outputData) {
        // Handle output data for clearing scores
        displayScores(outputData.getScores());
    }



    private void displayScores(Map<String, Integer> scores) {
        scores.forEach((key, value) -> System.out.println(key + ": " + value));
    }



}
