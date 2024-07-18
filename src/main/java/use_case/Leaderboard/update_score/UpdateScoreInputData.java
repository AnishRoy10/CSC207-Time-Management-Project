package use_case.Leaderboard.update_score;

public class UpdateScoreInputData {
    private final String username;
    private final int score;

    public UpdateScoreInputData(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
