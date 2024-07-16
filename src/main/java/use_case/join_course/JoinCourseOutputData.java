package use_case.join_course;

/**
 * An object to represent output data when joining a course.
 */
public class JoinCourseOutputData {
    
    final private boolean response;
    final private String message;

    /**
     * Construct a new Output Data object.
     * 
     * @param response success value of the prompt
     */
    public JoinCourseOutputData(boolean response, String message) {
        this.response = response;
        this.message = message;
    }

    /**
     * Get the response associated with this data object.
     * 
     * @return the response value
     */
    boolean getResponse() {
        return response;
    }

    /**
     * Gets the message associated with this output data.
     * 
     * @return the message
     */
    String getMessage() {
        return message;
    }
}
