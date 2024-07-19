package data_access;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import entity.Course;
import repositories.CourseRepository;

public class CourseDataAccessObject implements CourseRepository {
    private File fileCache;
    private String activeDirectory;

    public CourseDataAccessObject() throws IOException {

    }

    public CourseDataAccessObject(String path) throws IOException {

    }

    @Override
    public ArrayList<Course> ReadFromCache() throws IOException, ClassNotFoundException {
        ArrayList<Course> result = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileCache);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            result.add((Course) ois.readObject());
        } catch (EOFException e) {
            return null;
        } 
        return result;
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
