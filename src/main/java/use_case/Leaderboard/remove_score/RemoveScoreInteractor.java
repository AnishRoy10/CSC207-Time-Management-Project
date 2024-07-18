package use_case.Leaderboard.remove_score;

import entity.Leaderboard;

public class RemoveScoreInteractor implements RemoveScoreInputBoundary {
    private final Leaderboard leaderboard;
    private final RemoveScoreOutputBoundary presenter;

    public RemoveScoreInteractor(Leaderboard leaderboard, RemoveScoreOutputBoundary presenter) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
    }

    @Override
    public RemoveScoreOutputData removeScore(RemoveScoreInputData inputData) {
        leaderboard.removeScore(inputData.getUsername());
        RemoveScoreOutputData outputData = new RemoveScoreOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
