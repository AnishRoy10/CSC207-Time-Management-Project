package interface_adapter.controller;

import interface_adapter.presenter.LeaderboardPresenter;
import use_case.LeaderboardUseCase;
import entity.User;

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
     * @param user The user.
     * @param score The score to add.
     */
    public void addScore(User user, int score) {
        usecase.addScore(user, score);
    }

    /**
     * Removes a score from the leaderboard.
     * @param user The user.
     */
    public void removeScore(User user) {
        usecase.removeScore(user);
    }

    /**
     * Updates a score in the leaderboard.
     * @param user The user.
     * @param score The new score to add to current score.
     */
    public void updateScore(User user, int score) {
        usecase.updateScore(user, score);
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
