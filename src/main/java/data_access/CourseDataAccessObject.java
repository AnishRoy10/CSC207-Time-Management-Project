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
    public static ArrayList<Course> courses = new ArrayList<>();

    private final File fileCache;

    public CourseDataAccessObject(String path) throws IOException {
        this.fileCache = new File(path);
        if (!fileCache.exists()) {
            fileCache.createNewFile();
        }
    }

    @Override
    public ArrayList<Course> ReadFromCache() throws IOException, ClassNotFoundException {
        return courses;
    }

    @Override
    public boolean WriteToCache(Course course) {
        courses.removeIf(c -> c.getName().equals(course.getName()));
        courses.add(course);
        return true;
    }

    @Override
    public boolean courseExists(String courseName) {
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Course findByName(String courseName) {
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }
}
