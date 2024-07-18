package use_case.Leaderboard.remove_score;

public class RemoveScoreInputData {
    private final String username;

    public RemoveScoreInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
