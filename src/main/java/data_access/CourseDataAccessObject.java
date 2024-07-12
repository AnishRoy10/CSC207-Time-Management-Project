package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.Course;
import use_case.join_course.JoinCourseDataAccessInterface;

public class CourseDataAccessObject implements JoinCourseDataAccessInterface {

    private final Map<String, Course> courses = new HashMap<>();

    @Override
    public boolean courseExists(String courseName) {
        return courses.containsKey(courseName);
    }
}
