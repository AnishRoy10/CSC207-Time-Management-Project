package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.User;
import repositories.UserRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO for storing users in a JSON file
 */
public class FileCacheUserDataAccessObject implements UserRepository {
    private File fileCache;
    private String activeDirectory;
    private Map<String, User> userCache;
    private Gson gson;

    /**
     * Constructs a new FileCacheUserDataAccessObject and creates a new file if it doesn't exist.
     *
     * @throws IOException If an I/O error occurs.
     */
    public FileCacheUserDataAccessObject() throws IOException {
        activeDirectory = System.getProperty("user.dir");
        fileCache = new File(activeDirectory + "\\src\\main\\java\\data_access\\userCache.json");
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
        if (!fileCache.exists()) {
            fileCache.createNewFile();
            userCache = new HashMap<>();
            saveUserMap();
        } else {
            loadUserMap();
        }
    }

    /**
     * Constructs a new FileCacheUserDataAccessObject with a specified file path for testing.
     *
     * @param filePath The path to the cache file.
     * @throws IOException If an I/O error occurs.
     */
    public FileCacheUserDataAccessObject(String filePath) throws IOException {
        this.activeDirectory = null;
        this.fileCache = new File(filePath);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
        if (!fileCache.exists()) {
            fileCache.createNewFile();
            userCache = new HashMap<>();
            saveUserMap();
        } else {
            loadUserMap();
        }
    }

    /**
     * Writes a User object to the cache.
     *
     * @param user The User object to write.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void WriteToCache(User user) throws IOException {
        userCache.put(user.getUsername(), user);
        saveUserMap();
    }

    /**
     * Reads a User object from the cache by username.
     *
     * @param username The username of the user.
     * @return The User object read from the cache.
     */
    @Override
    public User ReadFromCache(String username) {
        return userCache.get(username);
    }

    /**
     * Checks if a user exists in the cache.
     *
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     */
    @Override
    public boolean UserExists(String username) {
        return userCache.containsKey(username);
    }

    /**
     * Finds a user by username.
     *
     * @param username The username to find.
     * @return The User object with the specified username.
     */
    @Override
    public User findByUsername(String username) {
        return userCache.get(username);
    }

    private void saveUserMap() throws IOException {
        try (FileWriter writer = new FileWriter(fileCache)) {
            gson.toJson(userCache, writer);
        }
    }

    private void loadUserMap() throws IOException {
        try (FileReader reader = new FileReader(fileCache)) {
            Type type = new TypeToken<Map<String, User>>() {}.getType();
            userCache = gson.fromJson(reader, type);
            if (userCache == null) {
                userCache = new HashMap<>();
            }
        }
    }
}
