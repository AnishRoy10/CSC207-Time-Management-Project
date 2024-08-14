package use_case.CourseUseCases.LoadCoursesUseCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Outgoing data for the load courses use case.
 */
public class LoadCoursesOutputData {
    /// The success response given by the interactor.
    private final boolean success;

    /// The message response given by the interactor.
    private final String message;

    /// The list of courses given by the interactor.
    private final List<String> courses;

    /**
     * Construct a new failed output data object.
     * @param success The success response of the use case.
     * @param message The message response of the use case.
     */
    public LoadCoursesOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.courses = new ArrayList<>();
    }

    /**
     * Construct a new successful output data object.
     * @param success The success response of the use case.
     * @param message The message response of the use case.
     * @param courses The list of courses to display.
     */
    public LoadCoursesOutputData(boolean success, String message, List<String> courses) {
        this.success = success;
        this.message = message;
        this.courses = courses;
    }

    /**
     * Get the success response embedded in this object.
     * @return The success response.
     */
    public boolean isSuccess() { return success; }

    /**
     * Get the message embedded in this object.
     * @return The message response.
     */
    public String getMessage() { return message; }

    /**
     * Get the course list embedded in this object.
     * @return The list of courses.
     */
    public List<String> getCourses() { return courses; }
}
