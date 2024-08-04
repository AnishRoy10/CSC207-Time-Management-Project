package use_case.CourseUseCases.LoadCoursesUseCase;

/**
 * Incoming data for the load courses use case.
 */
public class LoadCoursesInputData {
    /// The username of the current user.
    private final String username;

    /**
     * Construct a new input data object.
     * @param username The username of the current user.
     */
    public LoadCoursesInputData(String username) { this.username = username; }

    /**
     * Get the username of the current user.
     * @return The username.
     */
    public String getUsername() { return username; }
}
