package data_access;

import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;
import use_case.LeaderboardUseCases.add_score.AddScoreDataAccessInterface;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreDataAccessInterface;
import use_case.LeaderboardUseCases.update_score.UpdateScoreDataAccessInterface;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresDataAccessInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SQLLeaderboardDAOTest {

    private SQLDatabaseHelper dbHelper;
    private SQLLeaderboardDAO dao;

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:Saves/TestDB.db");
        dbHelper.initializeDatabase();
        dao = new SQLLeaderboardDAO(dbHelper);

        // Clear any existing data before each test
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Leaderboard");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        // Clear any test data after each test
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Leaderboard");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddScorePersistence() {
        dao.addScore("testUser", 500);

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery("SELECT * FROM Leaderboard WHERE username = 'testUser'");
            while (rs.next()) {
                assertEquals("testUser", rs.getString("username"));
                assertEquals(500, rs.getInt("score"));
                assertTrue(rs.getString("type").equals("DailyLeaderboard") ||
                        rs.getString("type").equals("MonthlyLeaderboard") ||
                        rs.getString("type").equals("AllTimeLeaderboard"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateScorePersistence() {
        dao.addScore("testUser", 500);
        dao.updateScore("testUser", 1000);

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery("SELECT * FROM Leaderboard WHERE username = 'testUser'");
            while (rs.next()) {
                assertEquals("testUser", rs.getString("username"));
                assertEquals(1000, rs.getInt("score"));
                assertTrue(rs.getString("type").equals("DailyLeaderboard") ||
                        rs.getString("type").equals("MonthlyLeaderboard") ||
                        rs.getString("type").equals("AllTimeLeaderboard"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemoveScorePersistence() {
        dao.addScore("testUser", 500);
        dao.removeScore("testUser");

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery("SELECT * FROM Leaderboard WHERE username = 'testUser'");
            assertFalse(rs.next()); // There should be no entries for 'testUser'
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testClearScoresPersistence() {
        dao.addScore("testUser1", 500);
        dao.addScore("testUser2", 300);
        dao.clearScores();

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery("SELECT * FROM Leaderboard");
            assertFalse(rs.next()); // There should be no entries in the Leaderboard
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadFromCache() {
        dao.addScore("testUser1", 500);
        dao.addScore("testUser2", 300);

        Map<String, Leaderboard> leaderboards = dao.readFromCache();
        assertNotNull(leaderboards);
        assertEquals(3, leaderboards.size());

        assertTrue(leaderboards.get("AllTimeLeaderboard").getScores().containsKey("testUser1"));
        assertTrue(leaderboards.get("AllTimeLeaderboard").getScores().containsKey("testUser2"));
        assertTrue(leaderboards.get("DailyLeaderboard").getScores().containsKey("testUser1"));
        assertTrue(leaderboards.get("DailyLeaderboard").getScores().containsKey("testUser2"));
        assertTrue(leaderboards.get("MonthlyLeaderboard").getScores().containsKey("testUser1"));
        assertTrue(leaderboards.get("MonthlyLeaderboard").getScores().containsKey("testUser2"));
    }
}
