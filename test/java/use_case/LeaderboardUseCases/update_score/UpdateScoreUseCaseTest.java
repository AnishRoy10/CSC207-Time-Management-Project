package use_case.LeaderboardUseCases.update_score;

import data_access.SQLDatabaseHelper;
import data_access.SQLLeaderboardDAO;
import entity.AllTimeLeaderboard;
import entity.Leaderboard;
import interface_adapter.presenter.LeaderboardPresenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateScoreUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private SQLLeaderboardDAO leaderboardDAO;
    private LeaderboardPresenter presenter;

    @BeforeEach
    void setUp() throws IOException {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:saves/TestDB.db");
        dbHelper.initializeDatabase();
        leaderboardDAO = new SQLLeaderboardDAO(dbHelper);
        presenter = new LeaderboardPresenter(new AllTimeLeaderboard("All-Time Leaderboard"));
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Leaderboard");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testUpdateScore() {
        Leaderboard leaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
        leaderboard.addScore("testUser", 100);
        assertDoesNotThrow(() -> {
            leaderboardDAO.writeToCache(Map.of("allTime", leaderboard));
            UpdateScoreUseCase updateScoreUseCase = new UpdateScoreUseCase(leaderboard, presenter, leaderboardDAO);

            UpdateScoreInputData inputData = new UpdateScoreInputData("testUser", 200);
            updateScoreUseCase.updateScore(inputData);

            assertEquals(200, leaderboard.getScores().get("testUser"));
        });
    }
}
