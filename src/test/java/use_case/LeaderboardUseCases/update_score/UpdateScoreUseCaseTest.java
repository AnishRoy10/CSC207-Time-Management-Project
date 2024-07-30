package use_case.LeaderboardUseCases.update_score;

import data_access.FileCacheLeaderboardDataAccessObject;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import interface_adapter.presenter.LeaderboardPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.LeaderboardUseCases.add_score.AddScoreInputBoundary;
import use_case.LeaderboardUseCases.add_score.AddScoreInputData;
import use_case.LeaderboardUseCases.add_score.AddScoreUseCase;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateScoreUseCaseTest {
    private UpdateScoreInputBoundary updateScoreUseCase;
    private AddScoreInputBoundary addScoreUseCase;
    private FileCacheLeaderboardDataAccessObject leaderboardDAO;
    private Leaderboard leaderboard;

    @BeforeEach
    public void setUp() throws IOException {
        leaderboard = new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now());
        leaderboardDAO = new FileCacheLeaderboardDataAccessObject("src/main/java/data_access/leaderboards.json");
        LeaderboardPresenter presenter = new LeaderboardPresenter(leaderboard);
        addScoreUseCase = new AddScoreUseCase(leaderboard, presenter, leaderboardDAO);
        updateScoreUseCase = new UpdateScoreUseCase(leaderboard, presenter, leaderboardDAO);
    }

    @Test
    public void testUpdateExistingScore() {
        addScoreUseCase.addScore(new AddScoreInputData("user1", 100));
        updateScoreUseCase.updateScore(new UpdateScoreInputData("user1", 200));

        Map<String, Integer> scores = leaderboard.getScores();
        assertEquals(1, scores.size());
        assertEquals(200, scores.get("user1"));
    }

}
