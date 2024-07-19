package interface_adapter.controller;

import interface_adapter.presenter.LeaderboardPresenter;
import use_case.LeaderboardUseCases.add_score.AddScoreInputBoundary;
import use_case.LeaderboardUseCases.add_score.AddScoreInputData;
import use_case.LeaderboardUseCases.add_score.AddScoreOutputData;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresInputBoundary;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresInputData;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresOutputData;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreInputBoundary;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreInputData;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreOutputData;
import use_case.LeaderboardUseCases.update_score.UpdateScoreInputBoundary;
import use_case.LeaderboardUseCases.update_score.UpdateScoreInputData;
import use_case.LeaderboardUseCases.update_score.UpdateScoreOutputData;

import javax.swing.*;

/**
 * LeaderboardController helps the interaction between the use case and the presenter.
 */
public class LeaderboardController {
    private final AddScoreInputBoundary addScoreUseCase;
    private final RemoveScoreInputBoundary removeScoreUseCase;
    private final UpdateScoreInputBoundary updateScoreUseCase;
    private final ClearScoresInputBoundary clearScoresUseCase;
    private final LeaderboardPresenter presenter;

    public LeaderboardController(AddScoreInputBoundary addScoreUseCase, RemoveScoreInputBoundary removeScoreUseCase,
                                 UpdateScoreInputBoundary updateScoreUseCase,
                                 ClearScoresInputBoundary clearScoreUseCase,
                                 LeaderboardPresenter presenter) {
        this.addScoreUseCase = addScoreUseCase;
        this.removeScoreUseCase = removeScoreUseCase;
        this.updateScoreUseCase = updateScoreUseCase;
        this.clearScoresUseCase = clearScoreUseCase;
        this.presenter = presenter;
    }

    /**
     * Adds a score to the leaderboard.
     * @param username The username of the user.
     * @param score The score to add.
     */
    public void addScore(String username, int score) {
        AddScoreInputData inputData = new AddScoreInputData(username, score);
        AddScoreOutputData outputData = addScoreUseCase.addScore(inputData);
        presenter.present(outputData);
    }

    /**
     * Removes a score from the leaderboard.
     * @param username The username of the user.
     */
    public void removeScore(String username) {
        RemoveScoreInputData inputData = new RemoveScoreInputData(username);
        RemoveScoreOutputData outputData = removeScoreUseCase.removeScore(inputData);
        presenter.present(outputData);
    }

    /**
     * Updates a score in the leaderboard.
     * @param username The username of the user.
     * @param score The new score to add to current score.
     */
    public void updateScore(String username, int score) {
        UpdateScoreInputData inputData = new UpdateScoreInputData(username, score);
        UpdateScoreOutputData  outputData = updateScoreUseCase.updateScore(inputData);
        presenter.present(outputData);
    }

    /**
     * Clears all scores from the leaderboard.
     */
    public void clearScores() {
        ClearScoresInputData inputData = new ClearScoresInputData();
        ClearScoresOutputData outputData = clearScoresUseCase.clearScores(inputData);
        presenter.present(outputData);

    }

    /**
     * Displays the leaderboard.
     */
    public void displayLeaderboard(JPanel panel) {
        presenter.displayLeaderboard(panel);
    }

}
























