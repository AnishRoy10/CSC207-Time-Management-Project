package use_case.CourseUseCases.LeaveCourseUseCase;

/**
 * Outgoing data for the leave course use case.
 */
public class LeaveCourseOutputData {
    /// The success response given by the interactor.
    private final boolean response;

    /// The message response given by the interactor.
    private final String message;

    /**
     * Construct a new output data object.
     * @param response The success response of the use case.
     * @param message  The message response of the use case.
     */
    public LeaveCourseOutputData(boolean response, String message) {
        this.response = response;
        this.message = message;
    }

    /**
     * Get the success response embedded in this object.
     * @return The success response.
     */
    public boolean getResponse() {
        return response;
    }

    /**
     * Get the message response embedded in this object.
     * @return The message response.
     */
    public String getMessage() {
        return message;
    }
}
