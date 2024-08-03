package use_case.LeaderboardUseCases.add_score;

import data_access.FileCacheLeaderboardDataAccessObject;
import entity.AllTimeLeaderboard;
import entity.Leaderboard;
import interface_adapter.presenter.LeaderboardPresenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddScoreUseCaseTest {
    private FileCacheLeaderboardDataAccessObject fileCacheLeaderboardDAO;
    private final String testFilePath = "test_leaderboardCache.json";
    private LeaderboardPresenter presenter;

    @BeforeEach
    void setUp() throws IOException {
        fileCacheLeaderboardDAO = new FileCacheLeaderboardDataAccessObject(testFilePath);
        presenter = new LeaderboardPresenter(new AllTimeLeaderboard("All-Time Leaderboard"));
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddScore() {
        Leaderboard leaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
        assertDoesNotThrow(() -> {
            fileCacheLeaderboardDAO.writeToCache(Map.of("allTime", leaderboard));
            AddScoreUseCase addScoreUseCase = new AddScoreUseCase(leaderboard, presenter, fileCacheLeaderboardDAO);

            AddScoreInputData inputData = new AddScoreInputData("testUser", 100);
            addScoreUseCase.addScore(inputData);

            assertEquals(100, leaderboard.getScores().get("testUser"));
        });
    }

    @Test
    void testAddScoreMultipleUsers() {
        Leaderboard leaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
        assertDoesNotThrow(() -> {
            fileCacheLeaderboardDAO.writeToCache(Map.of("allTime", leaderboard));
            AddScoreUseCase addScoreUseCase = new AddScoreUseCase(leaderboard, presenter, fileCacheLeaderboardDAO);

            AddScoreInputData inputData1 = new AddScoreInputData("user1", 50);
            AddScoreInputData inputData2 = new AddScoreInputData("user2", 150);
            addScoreUseCase.addScore(inputData1);
            addScoreUseCase.addScore(inputData2);

            assertEquals(50, leaderboard.getScores().get("user1"));
            assertEquals(150, leaderboard.getScores().get("user2"));
        });
    }
}
