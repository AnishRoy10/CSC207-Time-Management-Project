package data_access;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
public class FileCacheLeaderboardDataAccessObject implements LeaderboardRepository, AddScoreDataAccessInterface, RemoveScoreDataAccessInterface, UpdateScoreDataAccessInterface, ClearScoresDataAccessInterface, CacheDataAccessInterface {
    private final File fileCache;
    private final Gson gson;

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

    private void initializeEmptyFile() throws IOException {
        try (Writer writer = new FileWriter(fileCache)) {
            writer.write("{}");
        }
    }

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

    @Override
    public boolean leaderboardExists(String name) throws IOException {
        Map<String, Leaderboard> leaderboards = readFromCache();
        return leaderboards.containsKey(name);
    }

    @Override
    public Leaderboard findByName(String name) throws IOException {
        Map<String, Leaderboard> leaderboards = readFromCache();
        return leaderboards.get(name);
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
