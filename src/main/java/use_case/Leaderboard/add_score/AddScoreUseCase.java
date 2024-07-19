package use_case.Leaderboard.add_score;

import entity.Leaderboard;

/**
 * Interactor for adding a score to the leaderboard.
 */
public class AddScoreUseCase implements AddScoreInputBoundary {
    private final Leaderboard leaderboard;
    private final AddScoreOutputBoundary presenter;
    private final AddScoreDataAccessInterface dataAccess;


    /**
     * Constructs an AddScoreInteractor object.
     * @param leaderboard The leaderboard entity.
     * @param presenter The presenter for the output data.
     * @param dataAccess The data access interface for adding scores.
     */
    public AddScoreUseCase(Leaderboard leaderboard, AddScoreOutputBoundary presenter, AddScoreDataAccessInterface dataAccess) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Adds a score to the leaderboard.
     *
     * @param inputData The data to add the score.
     * @return The data after adding the score.
     */
    @Override
    public AddScoreOutputData addScore(AddScoreInputData inputData) {
        dataAccess.addScore(inputData.getUsername(), inputData.getScore());
        leaderboard.addScore(inputData.getUsername(), inputData.getScore());
        AddScoreOutputData outputData = new AddScoreOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
