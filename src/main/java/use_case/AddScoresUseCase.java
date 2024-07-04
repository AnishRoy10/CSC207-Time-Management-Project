package use_case;

import entity.Leaderboard;
import entity.User;

public class AddScoresUseCase {
    private final Leaderboard leaderboard;


    public AddScoresUseCase(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public void addScore(User user, int score) {
        leaderboard.addScore(user.username, score);
    }

    public void removeScore(User user, int score) {
        leaderboard.removeScore(user.username);
    }

    public void updateScore(User user, int score) {
        leaderboard.updateScore(user.username, score);
    }

    public void clearScores() {
        leaderboard.clearScores();
    }

    public void displayLeaderboard() {
        leaderboard.displayLeaderboard();
    }


}
