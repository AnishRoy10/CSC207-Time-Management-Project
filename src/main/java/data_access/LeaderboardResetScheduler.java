package data_access;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import entity.DailyLeaderboard;
import entity.Leaderboard;
import entity.MonthlyLeaderboard;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * The LeaderboardResetScheduler class is responsible for managing the reset schedules of leaderboards.
 * It handles loading reset dates from a configuration file, saving reset dates, and performing resets
 * for daily and monthly leaderboards.
 */
public class LeaderboardResetScheduler {

    private static final String CONFIG_FILE_PATH = "src/main/java/data_access/ResetSchedule.json";
    private LocalDate lastDailyReset;
    private LocalDate lastMonthlyReset;
    private Map<String, Leaderboard> leaderboards;


    /**
     * Constructs a LeaderboardResetScheduler and loads the reset dates from the configuration file.
     */
    public LeaderboardResetScheduler(Map<String, Leaderboard> leaderboards) {
        this.leaderboards = leaderboards;
        loadResetDates();
    }

    /**
     * Loads the reset dates from the configuration file. If the file does not exist, a default file will be created.
     */
    protected void loadResetDates() {
        try {
            Path path = Paths.get(CONFIG_FILE_PATH);
            if (!Files.exists(path)) {
                System.out.println("Config file does not exist, creating default config file.");
                createDefaultConfigFile();
            }
            String jsonContent = new String(Files.readAllBytes(path));
            JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
            lastDailyReset = jsonObject.has("lastDailyReset") ? LocalDate.parse(jsonObject.get("lastDailyReset").getAsString()) : null;
            lastMonthlyReset = jsonObject.has("lastMonthlyReset") ? LocalDate.parse(jsonObject.get("lastMonthlyReset").getAsString()) : null;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            // Handle error or set default dates
            lastDailyReset = null;
            lastMonthlyReset = null;
        }
    }

    /**
     * Creates a default configuration file with the current date as the last reset date.
     */
    private void createDefaultConfigFile() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("lastDailyReset", LocalDate.now().toString());
            jsonObject.addProperty("lastMonthlyReset", LocalDate.now().withDayOfMonth(1).toString());
            Files.write(Paths.get(CONFIG_FILE_PATH), jsonObject.toString().getBytes(), StandardOpenOption.CREATE);
            System.out.println("Default config file created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current reset dates to the configuration file.
     */
    private void saveResetDates() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("lastDailyReset", lastDailyReset != null ? lastDailyReset.toString() : LocalDate.now().toString());
            jsonObject.addProperty("lastMonthlyReset", lastMonthlyReset != null ? lastMonthlyReset.toString() : LocalDate.now().withDayOfMonth(1).toString());

            Gson gson = new Gson();
            Files.write(Paths.get(CONFIG_FILE_PATH), gson.toJson(jsonObject).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error
        }
    }

    /**
     * Checks and resets the leaderboards if necessary based on the last reset dates.
     * If the daily leaderboard has not been reset today, it resets.
     * If the monthly leaderboard has not been reset this month, it resets.
     */
    public void checkAndResetLeaderboards() {
        LocalDate today = LocalDate.now();

        // Reset daily leaderboard
        if (lastDailyReset == null || ChronoUnit.DAYS.between(lastDailyReset, today) >= 1) {
            resetDailyLeaderboard();
            lastDailyReset = today;
        }

        // Reset monthly leaderboard
        if (lastMonthlyReset == null || ChronoUnit.MONTHS.between(lastMonthlyReset, today) >= 1) {
            resetMonthlyLeaderboard();
            lastMonthlyReset = today.withDayOfMonth(1);
        }

        saveResetDates();
    }

    private void resetDailyLeaderboard() {
        for (Leaderboard leaderboard : leaderboards.values()) {
            if (leaderboard instanceof DailyLeaderboard) {
                leaderboard.clearScores();
                System.out.println("Daily leaderboard (" + leaderboard.getName() + ") has been reset.");
            }
        }
    }

    private void resetMonthlyLeaderboard() {
        for (Leaderboard leaderboard : leaderboards.values()) {
            if (leaderboard instanceof MonthlyLeaderboard) {
                leaderboard.clearScores();
                System.out.println("Monthly leaderboard (" + leaderboard.getName() + ") has been reset.");
            }
        }
    }

    /**
     * Returns the date of the last daily reset.
     *
     * @return the date of the last daily reset
     */
    public LocalDate getLastDailyReset() {
        return lastDailyReset;
    }

    /**
     * Returns the date of the last monthly reset.
     *
     * @return the date of the last monthly reset
     */
    public LocalDate getLastMonthlyReset() {
        return lastMonthlyReset;
    }
}
