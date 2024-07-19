package use_case.Leaderboard.clear_scores;

import entity.Leaderboard;

public class ClearScoresInteractor implements ClearScoresInputBoundary{
    private final Leaderboard leaderboard;
    private final ClearScoresOutputBoundary presenter;

    public ClearScoresInteractor(Leaderboard leaderboard, ClearScoresOutputBoundary presenter) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
    }

    @Override
    public ClearScoresOutputData clearScores(ClearScoresInputData inputData) {
        leaderboard.clearScores();
        ClearScoresOutputData outputData = new ClearScoresOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
