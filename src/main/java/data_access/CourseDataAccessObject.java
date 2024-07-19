package data_access;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import entity.Course;
import repositories.CourseRepository;

public class CourseDataAccessObject implements CourseRepository {
    private final File fileCache;
    private final String activeDirectory;

    public CourseDataAccessObject() throws IOException {
        activeDirectory = System.getProperty("courses.dir");
        System.out.println(activeDirectory);
        fileCache = new File(activeDirectory+"\\src\\main\\java\\data_access\\courseCache.txt");
        if (!fileCache.exists()) {
            fileCache.createNewFile();
        }
    }

    public CourseDataAccessObject(String path) throws IOException {
        this.activeDirectory = null;
        this.fileCache = new File(path);
        if (!fileCache.exists()) {
            fileCache.createNewFile();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Course> ReadFromCache() throws IOException, ClassNotFoundException {
        ArrayList<Course> result;
        try (FileInputStream fis = new FileInputStream(fileCache);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            result = (ArrayList<Course>) ois.readObject();
        } catch (EOFException e) {
            return null;
        } 
        return result;
    }

    @Override
    public void WriteToCache(Course course) throws IOException {
        ArrayList<Course> toWrite = new ArrayList<>();
        try {
            ArrayList<Course> temp = ReadFromCache();
            if (temp != null) {
                toWrite.addAll(temp);
            }
        } catch (ClassNotFoundException ignored) {
            // surely something will go here later
        }
        toWrite.add(course);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileCache))) {
            oos.writeObject(toWrite);
        }
    }

    @Override
    public boolean courseExists(String courseName) {
        try {
            ArrayList<Course> courses = ReadFromCache();
            for (Course crs : courses)
                if (crs.getName().equals(courseName)) return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        return false;
    }

    @Override
    public Course findByName(String courseName) {
        try {
            ArrayList<Course> courses = ReadFromCache();
            for (Course crs : courses)
                if (crs.getName().equals(courseName)) return crs;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return null;
    }
}
