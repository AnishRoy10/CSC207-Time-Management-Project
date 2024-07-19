package use_case.Leaderboard.clear_scores;

import entity.Leaderboard;

/**
 * Interactor for clearing scores from the leaderboard.
 */
public class ClearScoresUseCase implements ClearScoresInputBoundary{
    private final Leaderboard leaderboard;
    private final ClearScoresOutputBoundary presenter;
    private final ClearScoresDataAccessInterface dataAccess;


    /**
     * Constructs a ClearScoresInteractor object.
     *
     * @param leaderboard The leaderboard entity.
     * @param presenter The presenter for the output data.
     * @param dataAccess The data access interface for clearing scores.
     */
    public ClearScoresUseCase(Leaderboard leaderboard, ClearScoresOutputBoundary presenter, ClearScoresDataAccessInterface dataAccess) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Clears all scores from the leaderboard.
     *
     * @param inputData Data required to clear the scores.
     * @return Output data after clearing the scores.
     */
    @Override
    public ClearScoresOutputData clearScores(ClearScoresInputData inputData) {
        dataAccess.clearScores();
        leaderboard.clearScores();
        ClearScoresOutputData outputData = new ClearScoresOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
