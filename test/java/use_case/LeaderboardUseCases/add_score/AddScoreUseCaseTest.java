package use_case.LeaderboardUseCases.add_score;

import data_access.SQLDatabaseHelper;
import data_access.SQLLeaderboardDAO;
import entity.AllTimeLeaderboard;
import entity.Leaderboard;
import interface_adapter.presenter.LeaderboardPresenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddScoreUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private SQLLeaderboardDAO leaderboardDAO;
    private LeaderboardPresenter presenter;

    @BeforeEach
    void setUp() {
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
    void testAddScore() {
        Leaderboard leaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
        assertDoesNotThrow(() -> {
            leaderboardDAO.writeToCache(Map.of("allTime", leaderboard));
            AddScoreUseCase addScoreUseCase = new AddScoreUseCase(leaderboard, presenter, leaderboardDAO);

            AddScoreInputData inputData = new AddScoreInputData("testUser", 100);
            addScoreUseCase.addScore(inputData);

            assertEquals(100, leaderboard.getScores().get("testUser"));
        });
    }

    @Test
    void testAddScoreMultipleUsers() {
        Leaderboard leaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
        assertDoesNotThrow(() -> {
            leaderboardDAO.writeToCache(Map.of("allTime", leaderboard));
            AddScoreUseCase addScoreUseCase = new AddScoreUseCase(leaderboard, presenter, leaderboardDAO);

            AddScoreInputData inputData1 = new AddScoreInputData("user1", 50);
            AddScoreInputData inputData2 = new AddScoreInputData("user2", 150);
            addScoreUseCase.addScore(inputData1);
            addScoreUseCase.addScore(inputData2);

            assertEquals(50, leaderboard.getScores().get("user1"));
            assertEquals(150, leaderboard.getScores().get("user2"));
        });
    }
}
