package data_access;

import entity.Leaderboard;
import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.MonthlyLeaderboard;
import repositories.LeaderboardRepository;
import use_case.LeaderboardUseCases.add_score.AddScoreDataAccessInterface;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreDataAccessInterface;
import use_case.LeaderboardUseCases.update_score.UpdateScoreDataAccessInterface;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresDataAccessInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The SQLLeaderboardDAO class is responsible for managing the caching of Leaderboard objects using a SQL database.
 * This class provides methods to write a Leaderboard object to the cache, read a Leaderboard object from the cache,
 * and check if a Leaderboard exists in the cache.
 */
public class SQLLeaderboardDAO implements LeaderboardRepository, AddScoreDataAccessInterface, RemoveScoreDataAccessInterface, UpdateScoreDataAccessInterface, ClearScoresDataAccessInterface {
    private final SQLDatabaseHelper dbHelper;

    public SQLLeaderboardDAO(SQLDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Adds a score to all Leaderboards for the specified user.
     * @param username The username of the user.
     * @param score The score to add.
     */
    @Override
    public void addScore(String username, int score) {
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Leaderboard(username, score, type) VALUES(?, ?, ?) ON CONFLICT(username, type) DO UPDATE SET score = Leaderboard.score + ?")) {
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.setString(3, "AllTimeLeaderboard");
            pstmt.setInt(4, score);
            pstmt.executeUpdate();

            pstmt.setString(3, "DailyLeaderboard");
            pstmt.executeUpdate();

            pstmt.setString(3, "MonthlyLeaderboard");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the score of the specified user from all Leaderboards.
     * @param username The username of the user.
     */
    @Override
    public void removeScore(String username) {
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Leaderboard WHERE username = ?")) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the score of the specified user in all Leaderboards.
     * @param username The username of the user.
     * @param newScore The new score.
     */
    @Override
    public void updateScore(String username, int newScore) {
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE Leaderboard SET score = ? WHERE username = ? AND type = ?")) {
            pstmt.setInt(1, newScore);
            pstmt.setString(2, username);

            pstmt.setString(3, "AllTimeLeaderboard");
            pstmt.executeUpdate();

            pstmt.setString(3, "DailyLeaderboard");
            pstmt.executeUpdate();

            pstmt.setString(3, "MonthlyLeaderboard");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears all scores from the Leaderboard.
     */
    @Override
    public void clearScores() {
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Leaderboard")) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes Leaderboard objects to the cache.
     *
     * @param leaderboards A map of Leaderboard objects, with the key being the type of Leaderboard.
     */
    @Override
    public void writeToCache(Map<String, Leaderboard> leaderboards) {
        // Not required for SQL implementation
    }

    /**
     * Reads Leaderboard objects from the cache.
     *
     * @return A map of Leaderboard objects, with the key being the type of Leaderboard.
     */
    @Override
    public Map<String, Leaderboard> readFromCache() {
        Map<String, Leaderboard> leaderboards = new HashMap<>();
        leaderboards.put("AllTimeLeaderboard", new AllTimeLeaderboard("All Time Leaderboard"));
        leaderboards.put("DailyLeaderboard", new DailyLeaderboard("Daily Leaderboard", LocalDate.now()));
        leaderboards.put("MonthlyLeaderboard", new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now()));

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Leaderboard")) {
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                String type = rs.getString("type");

                switch (type) {
                    case "AllTimeLeaderboard":
                        leaderboards.get("AllTimeLeaderboard").addScore(username, score);
                        break;
                    case "DailyLeaderboard":
                        leaderboards.get("DailyLeaderboard").addScore(username, score);
                        break;
                    case "MonthlyLeaderboard":
                        leaderboards.get("MonthlyLeaderboard").addScore(username, score);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboards;
    }

    /**
     * Adds a new user to all leaderboards with a score of 0.
     * @param username The username of the new user.
     */
    @Override
    public void addNewUser(String username) {
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Leaderboard(username, score, type) VALUES(?, 0, ?)")) {
            pstmt.setString(1, username);

            pstmt.setString(2, "AllTimeLeaderboard");
            pstmt.executeUpdate();

            pstmt.setString(2, "DailyLeaderboard");
            pstmt.executeUpdate();

            pstmt.setString(2, "MonthlyLeaderboard");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
