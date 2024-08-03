package data_access;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import data_access.serializers.AllTimeLeaderboardDeserializer;
import data_access.serializers.DailyLeaderboardDeserializer;
import data_access.serializers.LocalDateSerializer;
import entity.Leaderboard;
import entity.AllTimeLeaderboard;
import entity.DailyLeaderboard;
import entity.MonthlyLeaderboard;
import repositories.LeaderboardRepository;
import use_case.LeaderboardUseCases.add_score.AddScoreDataAccessInterface;
import use_case.LeaderboardUseCases.remove_score.RemoveScoreDataAccessInterface;
import use_case.LeaderboardUseCases.update_score.UpdateScoreDataAccessInterface;
import use_case.LeaderboardUseCases.clear_scores.ClearScoresDataAccessInterface;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The FileCacheLeaderboardDataAccessObject class is responsible for managing the caching of Leaderboard objects
 * using a JSON file. This class provides methods to write a Leaderboard object to the cache, read a Leaderboard
 * object from the cache, and check if a Leaderboard exists in the cache.
 */
public class FileCacheLeaderboardDataAccessObject implements LeaderboardRepository, AddScoreDataAccessInterface, RemoveScoreDataAccessInterface, UpdateScoreDataAccessInterface, ClearScoresDataAccessInterface {
    private final File fileCache;
    private final Gson gson;

    /**
     * Constructs a FileCacheLeaderboardDataAccessObject with the specified file path.
     *
     * @param filePath the path to the file used for caching
     * @throws IOException if an I/O error occurs during file creation.
     */
    public FileCacheLeaderboardDataAccessObject(String filePath) throws IOException {
        this.fileCache = new File(filePath);
        if (!fileCache.exists()) {
            fileCache.createNewFile();
            initializeEmptyFile();
        }
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(AllTimeLeaderboard.class, new AllTimeLeaderboardDeserializer())
                .registerTypeAdapter(DailyLeaderboard.class, new DailyLeaderboardDeserializer())
                .registerTypeAdapter(MonthlyLeaderboard.class, new MonthlyLeaderboardDeserializer())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Initializes the file with an empty JSON object.
     *
     * @throws IOException if an I/0 occurs during file writing.
     */
    private void initializeEmptyFile() throws IOException {
        try (Writer writer = new FileWriter(fileCache)) {
            writer.write("{}");
        }
    }

    /**
     * Adds a score to all leaderboards.
     *
     * @param username the username of the player
     * @param score the score to be added
     */
    @Override
    public void addScore(String username, int score) {
        try {
            Map<String, Leaderboard> leaderboards = readFromCache();
            for (Leaderboard leaderboard : leaderboards.values()) {
                leaderboard.addScore(username, score);
                System.out.println("Updated leaderboard (" + leaderboard.getName() + ") scores: " + leaderboard.getScores());
            }
            writeToCache(leaderboards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a score from all leaderboards.
     *
     * @param username the username of the player
     */
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

    /**
     * Updates a score in all leaderboards.
     *
     * @param username the username of the player
     * @param newScore the new score to be updated
     */
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

    /**
     * Clears scores from all leaderboards.
     */
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

    /**
     * Writes the leaderboards to the cache file.
     *
     * @param leaderboards the leaderboards to be written
     * @throws IOException if an I/O error occurs during file writing
     */
    @Override
    public void writeToCache(Map<String, Leaderboard> leaderboards) throws IOException {
        try (Writer writer = new FileWriter(fileCache)) {
            JsonObject json = new JsonObject();
            for (Map.Entry<String, Leaderboard> entry : leaderboards.entrySet()) {
                Leaderboard leaderboard = entry.getValue();
                JsonObject leaderboardJson = gson.toJsonTree(leaderboard).getAsJsonObject();
                leaderboardJson.addProperty("type", leaderboard.getClass().getSimpleName());
                json.add(entry.getKey(), leaderboardJson);
            }
            gson.toJson(json, writer);
            System.out.println("Data written to cache: " + json.toString());
        }
    }

    /**
     * Reads the leaderboards from the cache file.
     *
     * @return the leaderboards read from the cache
     * @throws IOException if an I/O error occurs during file reading
     */
    @Override
    public Map<String, Leaderboard> readFromCache() throws IOException {
        if (fileCache.length() == 0) {
            initializeEmptyFile();
        }
        try (Reader reader = new FileReader(fileCache)) {
            Type leaderboardType = new TypeToken<Map<String, JsonObject>>() {}.getType();
            Map<String, JsonObject> jsonMap = gson.fromJson(reader, leaderboardType);
            Map<String, Leaderboard> leaderboards = new HashMap<>();
            for (Map.Entry<String, JsonObject> entry : jsonMap.entrySet()) {
                JsonObject leaderboardJson = entry.getValue();
                String type = leaderboardJson.get("type").getAsString();
                Leaderboard leaderboard = gson.fromJson(leaderboardJson, getLeaderboardClass(type));
                leaderboards.put(entry.getKey(), leaderboard);
            }
            System.out.println("Data read from cache: " + leaderboards);
            return leaderboards;
        }
    }

    /**
     * Returns the class of the leaderboard based on its type.
     *
     * @param type the type of the leaderboard
     * @return the class of the leaderboard
     */
    private Class<? extends Leaderboard> getLeaderboardClass(String type) {
        switch (type) {
            case "AllTimeLeaderboard":
                return AllTimeLeaderboard.class;
            case "DailyLeaderboard":
                return DailyLeaderboard.class;
            case "MonthlyLeaderboard":
                return MonthlyLeaderboard.class;
            default:
                throw new IllegalArgumentException("Unknown leaderboard type: " + type);
        }
    }


    /**
     * Adds a new user to all leaderboards with an initial score of 0.
     *
     * @param username the username of the new player
     * @throws IOException if an I/O error occurs during file operations
     */
    @Override
    public void addNewUser(String username) throws IOException {
        Map<String, Leaderboard> leaderboards = readFromCache();
        for (Leaderboard leaderboard : leaderboards.values()) {
            leaderboard.addScore(username, 0);
        }
        writeToCache(leaderboards);
    }
}
