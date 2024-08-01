package use_case.LeaderboardUseCases.add_score;

import data_access.FileCacheLeaderboardDataAccessObject;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import interface_adapter.presenter.LeaderboardPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddScoreUseCaseTest {
    private AddScoreInputBoundary addScoreUseCase;
    private FileCacheLeaderboardDataAccessObject leaderboardDAO;
    private Leaderboard leaderboard;

    @BeforeEach
    public void setUp() throws IOException {
        leaderboard = new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now());
        leaderboardDAO = new FileCacheLeaderboardDataAccessObject("src/main/java/data_access/leaderboards.json");
        LeaderboardPresenter presenter = new LeaderboardPresenter(leaderboard);
        addScoreUseCase = new AddScoreUseCase(leaderboard, presenter, leaderboardDAO);
    }

    @Test
    void testAddScore() {
        AddScoreInputData inputData = new AddScoreInputData("user1", 100);
        addScoreUseCase.addScore(inputData);

        Map<String, Integer> scores = leaderboard.getScores();
        assertEquals(1, scores.size());
        assertEquals(100, scores.get("user1"));
    }

    @Test
    void testAddMultipleScores() {
        addScoreUseCase.addScore(new AddScoreInputData("user1", 100));
        addScoreUseCase.addScore(new AddScoreInputData("user2", 200));
        addScoreUseCase.addScore(new AddScoreInputData("user3", 300));

        Map<String, Integer> scores = leaderboard.getScores();
        assertEquals(3, scores.size());
        assertEquals(100, scores.get("user1"));
        assertEquals(200, scores.get("user2"));
        assertEquals(300, scores.get("user3"));
    }


}

