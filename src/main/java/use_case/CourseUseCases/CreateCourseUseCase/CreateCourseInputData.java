package use_case.CourseUseCases.CreateCourseUseCase;

/**
 * Input data for the create course use case.
 */
public class CreateCourseInputData {
    private final String username;
    private final String courseName;
    private final String courseDescription;

    /**
     * Construct a new input data object.
     * @param username          The username of the current user.
     * @param courseName        The name of the course to create.
     * @param courseDescription The description of the course to create.
     */
    public CreateCourseInputData(String username, String courseName, String courseDescription) {
        this.username = username;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    /**
     * Get the username embedded in this data object.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the course name embedding in this data object.
     * @return The course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Get the course description embedded in this data object.
     * @return The course description.
     */
    public String getCourseDescription() {
        return courseDescription;
    }
}
