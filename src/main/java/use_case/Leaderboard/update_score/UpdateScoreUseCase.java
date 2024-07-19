package use_case.Leaderboard.update_score;

import entity.Leaderboard;

/**
 * Interactor for updating a score in the leaderboard.
 */
public class UpdateScoreUseCase implements UpdateScoreInputBoundary{
    private final Leaderboard leaderboard;
    private final UpdateScoreOutputBoundary presenter;
    private final UpdateScoreDataAccessInterface dataAccess;


    /**
     * Constructs an UpdateScoreInteractor object.
     *
     * @param leaderboard The leaderboard entity.
     * @param presenter The presenter for the output data.
     * @param dataAccess The data access interface for updating scores.
     */
    public UpdateScoreUseCase(Leaderboard leaderboard, UpdateScoreOutputBoundary presenter, UpdateScoreDataAccessInterface dataAccess) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Updates a score in the leaderboard.
     *
     * @param inputData Data required to update the score.
     * @return Output data after updating the score.
     */
    @Override
    public UpdateScoreOutputData updateScore(UpdateScoreInputData inputData) {
        dataAccess.updateScore(inputData.getUsername(), inputData.getScore());
        leaderboard.updateScore(inputData.getUsername(), inputData.getScore());
        UpdateScoreOutputData outputData = new UpdateScoreOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
