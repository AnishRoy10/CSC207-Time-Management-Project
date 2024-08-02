package data_access;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.nio.file.Path;

public class LeaderboardResetScheduler {

    private static final String CONFIG_FILE_PATH = "src/main/java/data_access/testresetSchedule.json";
    private LocalDate lastDailyReset;
    private LocalDate lastMonthlyReset;

    public LeaderboardResetScheduler() {
        loadResetDates();
    }

    private void loadResetDates() {
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
        // Implement your daily leaderboard reset logic here
        System.out.println("Daily leaderboard has been reset.");
    }

    private void resetMonthlyLeaderboard() {
        // Implement your monthly leaderboard reset logic here
        System.out.println("Monthly leaderboard has been reset.");
    }

    public LocalDate getLastDailyReset() {
        return lastDailyReset;
    }

    public LocalDate getLastMonthlyReset() {
        return lastMonthlyReset;
    }
}
