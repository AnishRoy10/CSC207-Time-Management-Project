package use_case.CourseUseCases.CreateCourseUseCase;

/**
 * Outgoing data for the create course use case.
 */
public class CreateCourseOutputData {
    /// The success response of the use case interactor.
    private final boolean response;

    /// The resulting message given by the use case interactor.
    private final String message;

    /**
     * Construct a new output data object.
     * @param response The success value of the use case.
     * @param message  The message to display to the user.
     */
    public CreateCourseOutputData(boolean response, String message) {
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
