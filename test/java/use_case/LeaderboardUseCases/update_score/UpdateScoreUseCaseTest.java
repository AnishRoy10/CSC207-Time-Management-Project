package use_case.LeaderboardUseCases.update_score;

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

class UpdateScoreUseCaseTest {
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
    void testUpdateScore() {
        Leaderboard leaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
        leaderboard.addScore("testUser", 100);
        assertDoesNotThrow(() -> {
            fileCacheLeaderboardDAO.writeToCache(Map.of("allTime", leaderboard));
            UpdateScoreUseCase updateScoreUseCase = new UpdateScoreUseCase(leaderboard, presenter, fileCacheLeaderboardDAO);

            UpdateScoreInputData inputData = new UpdateScoreInputData("testUser", 200);
            updateScoreUseCase.updateScore(inputData);

            assertEquals(200, leaderboard.getScores().get("testUser"));
        });
    }
}
