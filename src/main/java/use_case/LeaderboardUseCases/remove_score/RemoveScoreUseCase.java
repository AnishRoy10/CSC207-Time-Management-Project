package use_case.LeaderboardUseCases.remove_score;

import entity.Leaderboard;

/**
 * Interactor for removing a score from the leaderboard.
 */
public class RemoveScoreUseCase implements RemoveScoreInputBoundary {
    private final Leaderboard leaderboard;
    private final RemoveScoreOutputBoundary presenter;
    private final RemoveScoreDataAccessInterface dataAccess;


    /**
     * Constructs a RemoveScoreInteractor object.
     *
     * @param leaderboard The leaderboard entity.
     * @param presenter The presenter for the output data.
     * @param dataAccess The data access interface for removing scores.
     */
    public RemoveScoreUseCase(Leaderboard leaderboard, RemoveScoreOutputBoundary presenter, RemoveScoreDataAccessInterface dataAccess) {
        this.leaderboard = leaderboard;
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Removes a score from the leaderboard.
     *
     * @param inputData Data required to remove the score.
     * @return Output data after removing the score.
     */
    @Override
    public RemoveScoreOutputData removeScore(RemoveScoreInputData inputData) {
        dataAccess.removeScore(inputData.getUsername());
        leaderboard.removeScore(inputData.getUsername());
        RemoveScoreOutputData outputData = new RemoveScoreOutputData(leaderboard.getScores());
        presenter.present(outputData);
        return outputData;
    }
}
