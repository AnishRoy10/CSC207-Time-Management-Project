package use_case.join_course;

import entity.Course;
import entity.User;

/**
 * A use case to join new or existing users to a particular course.
 */
public class JoinCourseUseCase {
    private final Course course;
    private User user;

    /**
     * Construct a new use case instance without a given user.
     *
     * @param course course to add a user to
     */
    public JoinCourseUseCase(Course course) {
        this.course = course;
    }

    /**
     * Construct a new use case instance.
     *
     * @param course course to add user to
     * @param user   user to add to the course
     */
    public JoinCourseUseCase(Course course, User user) {
        this.course = course;
        this.user = user;
    }

    /**
     * Add a user to the course.
     */
    public final void addUser() {
        this.course.addUser(user);
    }

    /**
     * Add a user to the course.
     *
     * @param username username of the new user
     * @param friends  friends of the new user
     * @param courses  courses of the new user
     */
    public final void addUser(String username, User[] friends, Course[] courses) {
        this.user = new User(username, friends, courses);
        this.addUser();
    }
}
