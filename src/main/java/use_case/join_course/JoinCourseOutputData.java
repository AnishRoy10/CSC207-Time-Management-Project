package use_case.join_course;

/**
 * An object to represent output data when joining a course.
 */
public class JoinCourseOutputData {
    
    final private boolean useCaseFailed;

    /**
     * Construct a new Output Data object.
     * 
     * @param useCaseFailed success value of the use case
     */
    public JoinCourseOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Get the result associated with this data object.
     * @return if the use case failed
     */
    boolean getResult() {
        return useCaseFailed;
    }
}
