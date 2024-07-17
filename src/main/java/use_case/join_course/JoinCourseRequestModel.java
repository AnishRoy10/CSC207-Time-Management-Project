package use_case.join_course;

import entity.User;

/**
 * An object to represent input data when joining a course.
 */
public class JoinCourseRequestModel {
    
    final private User user;
    final private String courseName;

    /**
     * Construct a new Input Data object.
     * 
     * @param user   the user to add to course
     * @param course the course to add user to
     */
    public JoinCourseRequestModel(User user, String courseName) {
        this.user = user;
        this.courseName = courseName;
    }

    /**
     * Get the user associated with this data object.
     * @return the user
     */
    User getUser() {
        return user;
    }

    /**
     * Get the course name associated with this data object.
     * @return the course name
     */
    String getCourseName() {
        return courseName;
    }
}
