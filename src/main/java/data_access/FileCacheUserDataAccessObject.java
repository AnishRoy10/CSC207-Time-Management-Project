package data_access;

import entity.User;
import entity.Course;
import java.io.*;

/**
 * DAO for storing the active user in a txt file
 */

public class FileCacheUserDataAccessObject {
    private File fileCache;
    private String activeDirectory;

    // instantiates a new FileCacheUserDataAccessObject and creates a new txt file if it doesn't exist in the directory
    public FileCacheUserDataAccessObject() throws IOException {
        activeDirectory = System.getProperty("user.dir");
        System.out.println(activeDirectory);
        fileCache = new File(activeDirectory+"\\src\\main\\java\\data_access\\userCache.txt");
        fileCache.createNewFile();
    }
    // Takes a User object, serializes it, and writes it to the file (REWRITES THE FILE EVERY TIME THIS METHOD IS CALLED)
    public void WriteToCache(User userObject) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileCache);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(userObject);
        oos.close();
        fos.close();
    }
    // Reads the txt file and returns ONE User object
    public User ReadFromCache() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileCache);
        ObjectInputStream ois = new ObjectInputStream(fis);
        User userObject = (User) ois.readObject();
        ois.close();
        fis.close();
        return userObject;
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
