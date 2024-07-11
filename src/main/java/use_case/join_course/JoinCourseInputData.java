package use_case.join_course;

import entity.Course;
import entity.User;

public class JoinCourseInputData {
    
    final private User user;
    final private Course course;

    /**
     * Construct a new Input Data object.
     * 
     * @param user   the user to add to course
     * @param course the course to add user to
     */
    public JoinCourseInputData(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    /**
     * Get the user associated with this data object.
     * @return the user
     */
    User getUser() {
        return user;
    }

    /**
     * Get the course associated with this data object.
     * @return the course
     */
    Course getCourse() {
        return course;
    }
}
