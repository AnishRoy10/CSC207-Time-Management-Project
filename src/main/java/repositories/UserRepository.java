package repositories;

import entity.User;
import java.io.IOException;

/**
 * UserRepository provides an interface for User data access operations.
 */
public interface UserRepository {
    /**
     * Writes a User object to the cache.
     *
     * @param user The User object to write.
     * @throws IOException If an I/O error occurs.
     */
    void WriteToCache(User user) throws IOException;

    /**
     * Reads a User object from the cache.
     *
     * @return The User object read from the cache.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the User class is not found.
     */
    User ReadFromCache() throws IOException, ClassNotFoundException;

    /**
     * Checks if a user exists in the cache.
     *
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the User class is not found.
     */
    boolean UserExists(String username) throws IOException, ClassNotFoundException;
}
