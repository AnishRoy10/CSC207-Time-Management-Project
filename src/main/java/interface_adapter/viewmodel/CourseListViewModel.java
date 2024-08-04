package interface_adapter.viewmodel;

import java.util.List;

/**
 * View model to load courses into the course list.
 */
public class CourseListViewModel {
    /// The success response given by the use case.
    private boolean success;

    /// The message response given by the use case.
    private String message;

    /// The list of course names given by the use case.
    private List<String> courses;

    /**
     * Set the success value of the use case.
     * @param success The value to use.
     */
    public void setSuccess(boolean success) { this.success = success; }

    /**
     * Set the message value of the use case.
     * @param message The message to use.
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * Set the list of courses to display.
     * @param courses The list of course names.
     */
    public void setCourses(List<String> courses) { this.courses = courses; }

    /**
     * Get the success value.
     * @return The value.
     */
    public boolean isSuccess() { return success; }

    /**
     * Get the message response.
     * @return The message.
     */
    public String getMessage() { return message; }

    /**
     * Get the list of course names.
     * @return The course names.
     */
    public List<String> getCourses() { return courses; }
}
