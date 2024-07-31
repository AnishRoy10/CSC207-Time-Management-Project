package data_access;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.LeaderboardRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileCacheLeaderboardDataAccessObjectTest {

    private static final String FILE_PATH = "leaderboards.json";
    private FileCacheLeaderboardDataAccessObject dao;
    private Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException {
        dao = new FileCacheLeaderboardDataAccessObject(FILE_PATH);
        dao.writeToCache(Map.of(
                "daily", new DailyLeaderboard("Daily Leaderboard", LocalDate.now()),
                "monthly", new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now().withDayOfMonth(1)),
                "allTime", new AllTimeLeaderboard("All-Time Leaderboard")
        ));
    }

    @AfterEach
    void tearDown() {
        new File(FILE_PATH).delete();
    }

    @Test
    void testAddScorePersistence() throws IOException {
        dao.addScore("testUser", 500);

        JsonObject json = readJsonFromFile();
        assertEquals(500, json.getAsJsonObject("daily").getAsJsonObject("scores").get("testUser").getAsInt());
        assertEquals(500, json.getAsJsonObject("monthly").getAsJsonObject("scores").get("testUser").getAsInt());
        assertEquals(500, json.getAsJsonObject("allTime").getAsJsonObject("scores").get("testUser").getAsInt());
    }

    @Test
    void testUpdateScorePersistence() throws IOException {
        dao.addScore("testUser", 500);
        dao.updateScore("testUser", 1000);

        JsonObject json = readJsonFromFile();
        assertEquals(1000, json.getAsJsonObject("daily").getAsJsonObject("scores").get("testUser").getAsInt());
        assertEquals(1000, json.getAsJsonObject("monthly").getAsJsonObject("scores").get("testUser").getAsInt());
        assertEquals(1000, json.getAsJsonObject("allTime").getAsJsonObject("scores").get("testUser").getAsInt());
    }

    @Test
    void testRemoveScorePersistence() throws IOException {
        dao.addScore("testUser", 500);
        dao.removeScore("testUser");

        JsonObject json = readJsonFromFile();
        assertTrue(json.getAsJsonObject("daily").getAsJsonObject("scores").entrySet().isEmpty());
        assertTrue(json.getAsJsonObject("monthly").getAsJsonObject("scores").entrySet().isEmpty());
        assertTrue(json.getAsJsonObject("allTime").getAsJsonObject("scores").entrySet().isEmpty());
    }

    @Test
    void testClearScoresPersistence() throws IOException {
        dao.addScore("testUser1", 500);
        dao.addScore("testUser2", 300);
        dao.clearScores();

        JsonObject json = readJsonFromFile();
        assertTrue(json.getAsJsonObject("daily").getAsJsonObject("scores").entrySet().isEmpty());
        assertTrue(json.getAsJsonObject("monthly").getAsJsonObject("scores").entrySet().isEmpty());
        assertTrue(json.getAsJsonObject("allTime").getAsJsonObject("scores").entrySet().isEmpty());
    }

    private JsonObject readJsonFromFile() throws IOException {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }
}
