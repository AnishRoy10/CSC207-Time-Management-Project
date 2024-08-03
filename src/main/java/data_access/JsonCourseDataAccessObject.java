package data_access;

import entity.Course;
import repositories.CourseRepository;

import java.io.IOException;
import java.util.Map;

public class JsonCourseDataAccessObject implements CourseRepository {
    private final String path;


    public JsonCourseDataAccessObject(String path) {
        this.path = path;
    }

    @Override
    public Map<String, Course> ReadFromCache() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean WriteToCache(Course course) {
        return false;
    }

    @Override
    public boolean courseExists(String courseName) {
        return false;
    }

    @Override
    public Course findByName(String courseName) {
        return null;
    }
}
