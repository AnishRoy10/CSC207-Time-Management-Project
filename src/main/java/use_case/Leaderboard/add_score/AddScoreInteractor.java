package use_case.Leaderboard.add_score;

import entity.Leaderboard;

public class AddScoreInteractor implements AddScoreInputBoundary {
    private final Leaderboard leaderboard;
    private final AddScoreOutputBoundary presenter;

    public AddScoreInteractor(Leaderboard leaderboard, AddScoreOutputBoundary presenter) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
    }

    @Override
    public AddScoreOutputData addScore(AddScoreInputData inputData) {
        leaderboard.addScore(inputData.getUsername(), inputData.getScore());
        AddScoreOutputData outputData = new AddScoreOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
