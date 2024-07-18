package use_case.Leaderboard.update_score;

import entity.Leaderboard;

public class UpdateScoreInteractor implements UpdateScoreInputBoundary{
    private final Leaderboard leaderboard;
    private final UpdateScoreOutputBoundary presenter;

    public UpdateScoreInteractor(Leaderboard leaderboard, UpdateScoreOutputBoundary presenter) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
    }

    @Override
    public UpdateScoreOutputData updateScore(UpdateScoreInputData inputData) {
        leaderboard.updateScore(inputData.getUsername(), inputData.getScore());
        UpdateScoreOutputData outputData = new UpdateScoreOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
