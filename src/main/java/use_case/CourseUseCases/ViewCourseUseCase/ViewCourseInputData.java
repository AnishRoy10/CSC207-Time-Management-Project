package use_case.CourseUseCases.ViewCourseUseCase;

/**
 * Incoming data for the view course use case.
 */
public class ViewCourseInputData {
    /// The name of the course to display.
    String courseName;

    /**
     * Construct a new input data object.
     * @param courseName The name of the course to view.
     */
    public ViewCourseInputData(String courseName) { this.courseName = courseName; }

    /**
     * Get the name of the course embedded in this data object.
     * @return The course name.
     */
    public String getCourseName() { return courseName; }
}
