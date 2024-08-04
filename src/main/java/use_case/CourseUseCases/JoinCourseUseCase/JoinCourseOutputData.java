package use_case.CourseUseCases.JoinCourseUseCase;

/**
 * Outgoing data object for the join course use case.
 */
public class JoinCourseOutputData {
    /// The success response given by the interactor.
    final private boolean response;

    /// The message response given by the interactor.
    final private String message;

    /**
     * Construct a new output data object.
     * @param response The success of the use case.
     * @param message  The message to display to the user.
     */
    public JoinCourseOutputData(boolean response, String message) {
        this.response = response;
        this.message = message;
    }

    /**
     * Get the response embedded in this data object.
     * @return The success response.
     */
    public boolean getResponse() {
        return response;
    }

    /**
     * Get the message embedded in this data object.
     * @return The message.
     */
    public String getMessage() {
        return message;
    }
}
