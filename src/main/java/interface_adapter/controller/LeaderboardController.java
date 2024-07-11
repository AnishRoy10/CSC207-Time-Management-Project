package interface_adapter.controller;

import interface_adapter.presenter.LeaderboardPresenter;
import use_case.Leaderboard.LeaderboardUseCase;

/**
 * LeaderboardController helps the interaction between the use case and the presenter.
 */
public class LeaderboardController {
    private final LeaderboardUseCase usecase;
    private final LeaderboardPresenter presenter;

    public LeaderboardController(LeaderboardUseCase usecase, LeaderboardPresenter presenter) {
        this.usecase = usecase;
        this.presenter = presenter;
    }

    /**
     * Adds a score to the leaderboard.
     * @param username The username of the user.
     * @param score The score to add.
     */
    public void addScore(String username, int score) {
        usecase.addScore(username, score);
    }

    /**
     * Removes a score from the leaderboard.
     * @param username The username of the user.
     */
    public void removeScore(String username) {
        usecase.removeScore(username);
    }

    /**
     * Updates a score in the leaderboard.
     * @param username The username of the user.
     * @param score The new score to add to current score.
     */
    public void updateScore(String username, int score) {
        usecase.updateScore(username, score);
    }

    /**
     * Clears all scores from the leaderboard.
     */
    public void clearScores() {
        usecase.clearScores();
    }

    /**
     * Displays the leaderboard.
     */
    public void displayLeaderboard() {
        presenter.displayLeaderboard();
    }
}
