package use_case;

import entity.User;
import entity.Course;

public class JoinCourseUseCase {
    private Course course;
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
    public final void joinCourse() {
        this.course.addUser(user);
    }

    /**
     * Add a user to the course.
     *
     * @param username username of the new user
     * @param friends  friends of the new user
     * @param courses  courses of the new user
     */
    public final void joinCourse(String username, User[] friends, Course[] courses) {
        this.user = new User(username, friends, courses);
        this.joinCourse();
    }
}
