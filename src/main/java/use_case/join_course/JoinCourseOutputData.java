package use_case.join_course;

/**
 * An object to represent output data when joining a course.
 */
public class JoinCourseOutputData {
    
    final private boolean response;

    /**
     * Construct a new Output Data object.
     * 
     * @param response success value of the prompt
     */
    public JoinCourseOutputData(boolean response) {
        this.response = response;
    }

    /**
     * Get the response associated with this data object.
     * @return the response value
     */
    boolean getResponse() {
        return response;
    }
}
