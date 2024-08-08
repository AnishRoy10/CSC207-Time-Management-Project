//package data_access;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import data_access.serializers.LocalDateTimeSerializer;
//import entity.FriendsList;
//import entity.User;
//import repositories.UserRepository;
//
//import java.io.*;
//import java.lang.reflect.Type;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//
//// OUTDATED CODE SUPERSEDED BY UserDAO
///**
// * The FileCacheUserDataAccessObject class is responsible for managing the caching of User objects
// * using a JSON file. This class provides methods to write a User object to the cache, read a User
// * object from the cache, check if a User exists in the cache, and find a User by username.
// */
//public class FileCacheUserDataAccessObject implements UserRepository {
//    private final File fileCache;
//    private final Gson gson;
//
//    /**
//     * Constructs a new FileCacheUserDataAccessObject with the specified file path.
//     *
//     * @param filePath The path to the cache file.
//     * @throws IOException If an I/O error occurs.
//     */
//    public FileCacheUserDataAccessObject(String filePath) throws IOException {
//        this.fileCache = new File(filePath);
//        if (!fileCache.exists()) {
//            fileCache.createNewFile();
//        }
//        this.gson = new GsonBuilder()
//                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
//                .setPrettyPrinting()
//                .create();
//    }
//
//    /**
//     * Writes a User object to the cache.
//     *
//     * @param user The User object to write.
//     * @throws IOException If an I/O error occurs.
//     */
//    @Override
//    public void WriteToCache(User user) throws IOException {
//        Map<String, User> userMap = readAllUsers();
//        userMap.put(user.getUsername(), user);
//        try (Writer writer = new FileWriter(fileCache)) {
//            gson.toJson(userMap, writer);
//        }
//    }
//
//    /**
//     * Reads a User object from the cache. This method reads the first user from the cache file.
//     *
//     * @return The User object read from the cache, or null if the file is empty.
//     * @throws IOException If an I/O error occurs.
//     */
//    @Override
//    public User ReadFromCache() throws IOException {
//        if (fileCache.length() == 0) {
//            return null;
//        }
//        try (Reader reader = new FileReader(fileCache)) {
//            Type userType = new TypeToken<Map<String, User>>() {}.getType();
//            Map<String, User> userMap = gson.fromJson(reader, userType);
//            if (userMap != null && !userMap.isEmpty()) {
//                return userMap.values().iterator().next(); // Return the first user
//            }
//            return null;
//        }
//    }
//
//    /**
//     * Reads a User object from the cache by username.
//     *
//     * @param username The username of the user to read.
//     * @return The User object with the specified username, or null if not found.
//     * @throws IOException If an I/O error occurs.
//     */
//    @Override
//    public User ReadFromCache(String username) throws IOException {
//        if (fileCache.length() == 0) {
//            return null;
//        }
//        try (Reader reader = new FileReader(fileCache)) {
//            Type userType = new TypeToken<Map<String, User>>() {}.getType();
//            Map<String, User> userMap = gson.fromJson(reader, userType);
//            return userMap.get(username);
//        }
//    }
//
//    /**
//     * TEMPORARY METHOD Updates the specified users information in the cache and returns the user
//     * @param username
//     * @return
//     * @throws IOException
//     */
//    public User UpdateAndReadFromCache(String username) throws IOException {
//        if (fileCache.length() == 0) {
//            return null;
//        }
//        try (Reader reader = new FileReader(fileCache)) {
//            Type userType = new TypeToken<Map<String, User>>() {}.getType();
//            Map<String, User> userMap = gson.fromJson(reader, userType);
//            User user = userMap.get(username);
//            if (user != null) {
//                FriendsList friendsList = user.getFriends();
//                ArrayList<String> entriesToUpdate = user.getFriends().exportFriendsNames();
//                for(int i = 0; i < entriesToUpdate.size(); i++) {
//                    String entry = entriesToUpdate.get(i);
//                    friendsList.removeFriend(entry);
//                    friendsList.addFriend(userMap.get(entry));
//                }
//            }
//            WriteToCache(user);
//            return user;
//        }
//    }
//
//    /**
//     * Checks if a user exists in the cache.
//     *
//     * @param username The username to check.
//     * @return True if the user exists, false otherwise.
//     * @throws IOException If an I/O error occurs.
//     */
//    @Override
//    public boolean UserExists(String username) throws IOException {
//        User user = findByUsername(username);
//        return user != null;
//    }
//
//    /**
//     * Finds a user by username in the cache.
//     *
//     * @param username The username to find.
//     * @return The User object with the specified username, or null if not found.
//     * @throws IOException If an I/O error occurs.
//     */
//    @Override
//    public User findByUsername(String username) throws IOException {
//        return ReadFromCache(username);
//    }
//
//    /**
//     * TEMPORARY METHOD Updates and finds a user by username in the cache.
//     *
//     * @param username The username to find.
//     * @return The User object with the specified username, or null if not found.
//     * @throws IOException If an I/O error occurs.
//     */
//    public User updateAndFindByUsername(String username) throws IOException {
//        return UpdateAndReadFromCache(username);
//    }
//
//    private Map<String, User> readAllUsers() throws IOException {
//        if (fileCache.length() == 0) {
//            return new HashMap<>();
//        }
//        try (Reader reader = new FileReader(fileCache)) {
//            Type userType = new TypeToken<Map<String, User>>() {}.getType();
//            Map<String, User> userMap = gson.fromJson(reader, userType);
//            return userMap != null ? userMap : new HashMap<>();
//        }
//    }
//}
