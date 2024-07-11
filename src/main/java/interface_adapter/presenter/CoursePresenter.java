package presenter;

import entity.Course;

/**
 * CoursePresenter displays course-associated widgets.
 */
public class CoursePresenter {
    private final Course course;

    public CoursePresenter(Course course) {
        this.course = course;
    }

    public void displayCourse() {};
}
