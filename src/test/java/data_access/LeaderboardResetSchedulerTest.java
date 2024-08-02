package data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardResetSchedulerTest {

    private static final String CONFIG_FILE_PATH = "src/main/java/data_access/testresetSchedule.json";

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary testresetSchedule.json file with default values for testing
        String initialContent = "{ \"lastDailyReset\": \"" + LocalDate.now().minusDays(1).toString() + "\", \"lastMonthlyReset\": \"" + LocalDate.now().minusMonths(1).withDayOfMonth(1).toString() + "\" }";
        Files.write(Paths.get(CONFIG_FILE_PATH), initialContent.getBytes());
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up by deleting the temporary testresetSchedule.json file after each test
        Files.deleteIfExists(Paths.get(CONFIG_FILE_PATH));
    }

    @Test
    void testLoadResetDates() {
        LeaderboardResetScheduler resetManager = new LeaderboardResetScheduler();
        assertNotNull(resetManager);
        assertNotNull(resetManager.getLastDailyReset());
        assertNotNull(resetManager.getLastMonthlyReset());
    }

    @Test
    void testCheckAndResetLeaderboards() {
        LeaderboardResetScheduler resetManager = new LeaderboardResetScheduler();
        resetManager.checkAndResetLeaderboards();

        LocalDate today = LocalDate.now();
        assertEquals(today, resetManager.getLastDailyReset());
        assertEquals(today.withDayOfMonth(1), resetManager.getLastMonthlyReset());
    }

    @Test
    void testCreateDefaultConfigFile() {
        // Delete the config file to simulate a missing file scenario
        try {
            Files.deleteIfExists(Paths.get(CONFIG_FILE_PATH));
            System.out.println("Config file deleted to simulate missing file scenario.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        LeaderboardResetScheduler resetManager = new LeaderboardResetScheduler();
        resetManager.checkAndResetLeaderboards();

        boolean fileExists = Files.exists(Paths.get(CONFIG_FILE_PATH));
        System.out.println("Config file exists: " + fileExists);
        assertTrue(fileExists, "Config file should be created");

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(CONFIG_FILE_PATH)));
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
