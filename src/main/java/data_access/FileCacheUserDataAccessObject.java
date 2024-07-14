package data_access;

import entity.User;

import java.io.*;

public class FileCacheUserDataAccessObject {
    private File fileCache;
    private String activeDirectory;

    public FileCacheUserDataAccessObject() throws IOException {
        activeDirectory = System.getProperty("user.dir");
        fileCache = new File(activeDirectory+"/userCache.txt");
        fileCache.createNewFile();
    }
    public void WriteToCache(User userObject) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileCache);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(userObject);
        oos.close();
        fos.close();
    }
    public User ReadFromCache() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileCache);
        ObjectInputStream ois = new ObjectInputStream(fis);
        User userObject = (User) ois.readObject();
        ois.close();
        fis.close();
        return userObject;
    }
}
