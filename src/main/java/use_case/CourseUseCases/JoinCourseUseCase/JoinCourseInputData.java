package use_case.CourseUseCases.JoinCourseUseCase;

/**
 * Incoming data for the join course use case.
 */
public class JoinCourseInputData {
    /// The username of the currently logged-in user.
    private final String username;

    /// The name of the course to join.
    private final String courseName;

    /**
     * Construct a new input data object.
     * @param username   The username of the logged-in user.
     * @param courseName The name of the course to join the user to.
     */
    public JoinCourseInputData(String username, String courseName) {
        this.username = username;
        this.courseName = courseName;
    }

    /**
     * Get the username embedded in this data object.
     * @return The username of the logged-in user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the course name embedded in this data object.
     * @return The name of the course to join.
     */
    public String getCourseName() {
        return courseName;
    }
}
