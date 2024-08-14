package repositories;

import entity.Leaderboard;
import java.io.IOException;
import java.util.Map;

/**
 * LeaderboardRepository provides an interface for Leaderboard data access operations.
 */
public interface LeaderboardRepository {
    /**
     * Writes a Leaderboard object to the cache.
     *
     * @param leaderboards The map of Leaderboard objects to write.
     * @throws IOException If an I/O error occurs.
     */
    void writeToCache(Map<String, Leaderboard> leaderboards) throws IOException;

    /**
     * Reads Leaderboard objects from the cache.
     *
     * @return A map of Leaderboard objects read from the cache.
     * @throws IOException If an I/O error occurs.
     */
    Map<String, Leaderboard> readFromCache() throws IOException;

    /**
     * Adds a new user to all leaderboards with a score of 0.
     *
     * @param username The username of the new user.
     * @throws IOException If an I/O error occurs.
     */
    void addNewUser(String username) throws IOException;
}
