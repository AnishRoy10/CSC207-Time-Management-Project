package use_case.LeaderboardUseCases.clear_scores;

import data_access.LeaderboardDataAccessObject;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import interface_adapter.presenter.LeaderboardPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.LeaderboardUseCases.add_score.AddScoreInputBoundary;
import use_case.LeaderboardUseCases.add_score.AddScoreInputData;
import use_case.LeaderboardUseCases.add_score.AddScoreUseCase;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearScoresUseCaseTest {
    private ClearScoresInputBoundary clearScoresUseCase;
    private AddScoreInputBoundary addScoreUseCase;
    private LeaderboardDataAccessObject leaderboardDAO;
    private Leaderboard leaderboard;

    @BeforeEach
    public void setUp() {
        leaderboard = new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now());
        leaderboardDAO = new LeaderboardDataAccessObject();
        LeaderboardPresenter presenter = new LeaderboardPresenter(leaderboard);
        addScoreUseCase = new AddScoreUseCase(leaderboard, presenter, leaderboardDAO);
        clearScoresUseCase = new ClearScoresUseCase(leaderboard, presenter, leaderboardDAO);
    }

    @Test
    public void testClearScores() {
        addScoreUseCase.addScore(new AddScoreInputData("user1", 100));
        addScoreUseCase.addScore(new AddScoreInputData("user2", 200));
        clearScoresUseCase.clearScores(new ClearScoresInputData());

        Map<String, Integer> scores = leaderboard.getScores();
        assertEquals(0, scores.size());
    }

    @Test
    public void testClearScoresWhenAlreadyEmpty() {
        clearScoresUseCase.clearScores(new ClearScoresInputData());

        Map<String, Integer> scores = leaderboard.getScores();
        assertEquals(0, scores.size());
    }
}
