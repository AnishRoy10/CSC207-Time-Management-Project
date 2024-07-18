package use_case.Leaderboard.add_score;

public class AddScoreInputData {
    private final String username;
    private final int score;

    public AddScoreInputData(String username, int score) {
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
