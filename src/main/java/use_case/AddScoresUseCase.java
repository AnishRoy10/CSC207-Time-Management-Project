package use_case;

import entity.Leaderboard;

public class AddScoresUseCase {
    private final Leaderboard leaderboard;


    public AddScoresUseCase(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public void addScore(String username, int score) {
        leaderboard.addScore(username, score);
    }

    public void removeScore(String username, int score) {
        leaderboard.removeScore(username);
    }

    public void updateScore(String username, int score) {
        leaderboard.updateScore(username, score);
    }

    public void clearScores() {
        leaderboard.clearScores();
    }

    public void displayLeaderboard() {
        leaderboard.displayLeaderboard();
    }
}
