package use_case.UserUseCases.UserLoginUseCase;

/**
 * Response model for the user login use case.
 * This model encapsulates the result of the login process, including whether it was successful and any relevant messages.
 */
public class UserLoginResponseModel {
    private final boolean success;
    private final String message;

    /**
     * Constructs a {@code UserLoginResponseModel} with the specified success status and message.
     *
     * @param success The success status of the login attempt.
     * @param message The message related to the login attempt, providing additional context or information.
     */
    public UserLoginResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Returns whether the login attempt was successful.
     *
     * @return {@code true} if the login was successful, otherwise {@code false}.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the message related to the login attempt.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }
}
