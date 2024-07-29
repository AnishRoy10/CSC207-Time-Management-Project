package data_access;

import entity.Leaderboard;

import java.io.IOException;
import java.util.Map;

/**
 * Data access interface for caching leaderboards.
 */
public interface CacheDataAccessInterface {
    void writeToCache(Map<String, Leaderboard> leaderboards) throws IOException;
    Map<String, Leaderboard> readFromCache() throws IOException;
    boolean leaderboardExists(String name) throws IOException;
    Leaderboard findByName(String name) throws IOException;
}
