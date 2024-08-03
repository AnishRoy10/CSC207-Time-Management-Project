package data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LeaderboardResetScheduler.
 * This class tests the functionality of the LeaderboardResetScheduler, including loading reset dates,
 * checking and resetting leaderboards, and creating default configuration files.
 */
class LeaderboardResetSchedulerTest {

    private static final String CONFIG_FILE_PATH = "src/main/java/data_access/ResetSchedule.json";
    private Map<String, Leaderboard> leaderboards;
    private Path configFilePath;

    /**
     * Sets up the test environment by creating a temporary testResetSchedule.json file with default values.
     *
     * @throws IOException if an I/O error occurs during file creation
     */
    @BeforeEach
    void setUp() throws IOException {
        configFilePath = Paths.get(CONFIG_FILE_PATH);

        // Create directories if they don't exist
        Files.createDirectories(configFilePath.getParent());

        // Create a temporary testResetSchedule.json file with default values for testing
        String initialContent = "{ \"lastDailyReset\": \"" + LocalDate.now().minusDays(1) + "\", \"lastMonthlyReset\": \"" + LocalDate.now().minusMonths(1).withDayOfMonth(1) + "\" }";
        Files.write(configFilePath, initialContent.getBytes());

        // Initialize leaderboards
        leaderboards = new HashMap<>();
        leaderboards.put("daily", new DailyLeaderboard("Daily Leaderboard", LocalDate.now().minusDays(1)));
        leaderboards.put("monthly", new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now().minusMonths(1).withDayOfMonth(1)));
        leaderboards.put("allTime", new AllTimeLeaderboard("All-Time Leaderboard"));
    }

    /**
     * Cleans up the test environment by deleting the temporary testResetSchedule.json file after each test.
     *
     * @throws IOException if an I/O error occurs during file deletion
     */
    @AfterEach
    void tearDown() throws IOException {
        // Clean up by deleting the temporary testResetSchedule.json file after each test
        Files.deleteIfExists(configFilePath);
    }

    /**
     * Tests the loading of reset dates from the configuration file.
     */
    @Test
    void testLoadResetDates() {
        LeaderboardResetScheduler resetManager = new LeaderboardResetScheduler(leaderboards);
        assertNotNull(resetManager);
        assertNotNull(resetManager.getLastDailyReset());
        assertNotNull(resetManager.getLastMonthlyReset());
    }

    /**
     * Tests the checking and resetting of leaderboards.
     */
    @Test
    void testCheckAndResetLeaderboards() {
        LeaderboardResetScheduler resetManager = new LeaderboardResetScheduler(leaderboards);
        resetManager.checkAndResetLeaderboards();

        LocalDate today = LocalDate.now();
        assertEquals(today, resetManager.getLastDailyReset());
        assertEquals(today.withDayOfMonth(1), resetManager.getLastMonthlyReset());
    }

    /**
     * Tests the creation of the default configuration file when it is missing.
     */
    @Test
    void testCreateDefaultConfigFile() {
        // Delete the config file to simulate a missing file scenario
        try {
            Files.delete(configFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LeaderboardResetScheduler resetManager = new LeaderboardResetScheduler(leaderboards);

        resetManager.loadResetDates();
        resetManager.checkAndResetLeaderboards();

        boolean fileExists = Files.exists(configFilePath);
        assertTrue(fileExists, "Config file should be created");

        try {
            String jsonContent = new String(Files.readAllBytes(configFilePath));
            assertNotNull(jsonContent, "Config file content should not be null");
            assertFalse(jsonContent.isEmpty(), "Config file content should not be empty");

            JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
            LocalDate lastDailyReset = LocalDate.parse(jsonObject.get("lastDailyReset").getAsString());
            LocalDate lastMonthlyReset = LocalDate.parse(jsonObject.get("lastMonthlyReset").getAsString());

            assertEquals(LocalDate.now(), lastDailyReset);
            assertEquals(LocalDate.now().withDayOfMonth(1), lastMonthlyReset);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception while reading the config file");
        }
    }
}
