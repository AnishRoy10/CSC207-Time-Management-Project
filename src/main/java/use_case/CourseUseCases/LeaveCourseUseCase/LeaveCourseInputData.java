package use_case.CourseUseCases.LeaveCourseUseCase;

/**
 * Incoming data object for the leave course use case.
 */
public class LeaveCourseInputData {
    /// The username of the logged-in user.
    private final String username;

    /// The name of the course to leave.
    private final String courseName;

    /**
     * Construct a new leave course data object.
     * @param username   The username of the current user.
     * @param courseName The name of the course to leave.
     */
    public LeaveCourseInputData(String username, String courseName) {
        this.username = username;
        this.courseName = courseName;
    }

    /**
     * Get the username embedded in this data object.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the course name embedded in this data object.
     * @return The course name.
     */
    public String getCourseName() {
        return courseName;
    }
}
