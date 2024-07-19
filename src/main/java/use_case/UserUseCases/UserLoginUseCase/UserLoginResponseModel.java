package use_case.UserUseCases.UserLoginUseCase;

/**
 * Response model for the user login use case.
 */
public class UserLoginResponseModel {
    private final boolean success;
    private final String message;

    /**
     * Constructs a UserLoginResponseModel with the specified success status and message.
     *
     * @param success The success status of the login attempt.
     * @param message The message related to the login attempt.
     */
    public UserLoginResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Gets the success status.
     *
     * @return True if the login was successful, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the message.
     *
     * @return The message related to the login attempt.
     */
    public String getMessage() {
        return message;
    }
}
