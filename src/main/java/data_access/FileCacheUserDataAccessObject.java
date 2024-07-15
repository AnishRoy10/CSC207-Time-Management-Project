package data_access;

import entity.User;
import entity.Course;
import repositories.UserRepository;
import java.io.*;

/**
 * DAO for storing the active user in a txt file
 */

public class FileCacheUserDataAccessObject implements UserRepository{
    private File fileCache;
    private String activeDirectory;

    /**
     * Constructs a new FileCacheUserDataAccessObject and creates a new file if it doesn't exist.
     *
     * @throws IOException If an I/O error occurs.
     */
    public FileCacheUserDataAccessObject() throws IOException {
        activeDirectory = System.getProperty("user.dir");
        System.out.println(activeDirectory);
        fileCache = new File(activeDirectory+"\\src\\main\\java\\data_access\\userCache.txt");
        if (!fileCache.exists()) {
            fileCache.createNewFile();
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
        try (FileOutputStream fos = new FileOutputStream(fileCache);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        }
    }

    /**
     * Reads a User object from the cache.
     *
     * @return The User object read from the cache.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the User class is not found.
     */
    public User ReadFromCache() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileCache);
        ObjectInputStream ois = new ObjectInputStream(fis);
        User userObject = (User) ois.readObject();
        ois.close();
        fis.close();
        return userObject;
    }

    /**
     * Checks if a user exists in the cache by reading the cache and checking the username.
     *
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the User class is not found.
     */
    @Override
    public boolean UserExists(String username) throws IOException, ClassNotFoundException {
        User cachedUser = ReadFromCache();
        return cachedUser != null && cachedUser.getUsername().equals(username);
    }
    /*  This commented block is for testing user read/write to file
    public void TestUserSerialization() throws IOException, ClassNotFoundException {
        User[] users = new User[1];
        User[] emtpyUsers = new User[10];
        Course[] courses = new Course[1];
        Course dummyCourse = new Course("CSC207", "Software Design");
        courses[0] = dummyCourse;
        User dummy = new User("user2", emtpyUsers, courses);
        users[0] = dummy;
        User newUser = new User("user1", users, courses);
        WriteToCache(newUser);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileCacheUserDataAccessObject f = new FileCacheUserDataAccessObject();
        f.TestUserSerialization();
        User readUser = f.ReadFromCache();
        System.out.println(readUser.getUsername());
        System.out.println(readUser.getFriends().exportFriendsNames());
        System.out.println(readUser.getCourses().get(0).getName());
    }
*/
}