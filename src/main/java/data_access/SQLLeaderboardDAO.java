package data_access;

import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import repositories.LeaderboardRepository;
import use_case.LeaderboardUseCases.add_score.AddScoreDataAccessInterface;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreDataAccessInterface;
import use_case.LeaderboardUseCases.update_score.UpdateScoreDataAccessInterface;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresDataAccessInterface;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SQLLeaderboardDAO implements LeaderboardRepository, AddScoreDataAccessInterface, RemoveScoreDataAccessInterface, UpdateScoreDataAccessInterface, ClearScoresDataAccessInterface {
    private final SQLDatabaseHelper dbHelper;

    public SQLLeaderboardDAO(SQLDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
        dbHelper.initializeDatabase();
    }

    @Override
    public void addScore(String username, int score) {
        try {
            Map<String, Leaderboard> leaderboards = readFromCache();
            for (Leaderboard leaderboard : leaderboards.values()) {
                leaderboard.addScore(username, score);
            }
            writeToCache(leaderboards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeScore(String username) {
        try {
            Map<String, Leaderboard> leaderboards = readFromCache();
            for (Leaderboard leaderboard : leaderboards.values()) {
                leaderboard.removeScore(username);
            }
            writeToCache(leaderboards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScore(String username, int newScore) {
        try {
            Map<String, Leaderboard> leaderboards = readFromCache();
            for (Leaderboard leaderboard : leaderboards.values()) {
                leaderboard.updateScore(username, newScore);
            }
            writeToCache(leaderboards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearScores() {
        try {
            Map<String, Leaderboard> leaderboards = readFromCache();
            for (Leaderboard leaderboard : leaderboards.values()) {
                leaderboard.clearScores();
            }
            writeToCache(leaderboards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToCache(Map<String, Leaderboard> leaderboards) throws IOException {
        String sqlDeleteScores = "DELETE FROM Leaderboard";
        String sqlInsertScore = "INSERT INTO Leaderboard(username, score, type) VALUES(?, ?, ?)";

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {

            // Clear existing data
            stmt.execute(sqlDeleteScores);

            try (PreparedStatement pstmtScore = conn.prepareStatement(sqlInsertScore)) {

                for (Map.Entry<String, Leaderboard> entry : leaderboards.entrySet()) {
                    Leaderboard leaderboard = entry.getValue();
                    String leaderboardType = leaderboard.getType();

                    for (Map.Entry<String, Integer> scoreEntry : leaderboard.getScores().entrySet()) {
                        pstmtScore.setString(1, scoreEntry.getKey());
                        pstmtScore.setInt(2, scoreEntry.getValue());
                        pstmtScore.setString(3, leaderboardType);
                        pstmtScore.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Map<String, Leaderboard> readFromCache() {
        Map<String, Leaderboard> leaderboards = new HashMap<>();

        // Initialize all types of leaderboards
        leaderboards.put("AllTimeLeaderboard", new AllTimeLeaderboard("AllTimeLeaderboard"));
        leaderboards.put("MonthlyLeaderboard", new MonthlyLeaderboard("MonthlyLeaderboard", LocalDate.now()));
        leaderboards.put("DailyLeaderboard", new DailyLeaderboard("DailyLeaderboard", LocalDate.now()));

        String sqlSelectLeaderboards = "SELECT * FROM Leaderboard";

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectLeaderboards)) {

            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                String type = rs.getString("type");

                Leaderboard leaderboard = leaderboards.get(type);
                if (leaderboard != null) {
                    leaderboard.addScore(username, score);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return leaderboards;
    }

    @Override
    public void addNewUser(String username) throws IOException {
        Map<String, Leaderboard> leaderboards = readFromCache();
        for (Leaderboard leaderboard : leaderboards.values()) {
            leaderboard.addScore(username, 0);
        }
        writeToCache(leaderboards);
    }
}
