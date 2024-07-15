package use_case.UserSignupUseCase;

/**
 * Response model for user sign-up use case.
 */
public class UserSignupResponseModel {
    private final boolean success;
    private final String message;

    /**
     * Constructs a UserSignupResponseModel with the specified success status and message.
     *
     * @param success  Whether the sign-up was successful.
     * @param message  A message describing the outcome of the sign-up process.
     */
    public UserSignupResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
