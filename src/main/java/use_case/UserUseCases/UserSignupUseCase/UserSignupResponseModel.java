package use_case.UserUseCases.UserSignupUseCase;

import java.util.Objects;

/**
 * Response model for the user signup use case.
 * This model encapsulates the result of the signup process, including whether it was successful and any relevant messages.
 */
public class UserSignupResponseModel {
    private final boolean success;
    private final String message;

    /**
     * Constructs a {@code UserSignupResponseModel} with the specified success status and message.
     *
     * @param success The success status of the signup process.
     * @param message The message associated with the signup process.
     */
    public UserSignupResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Returns whether the signup process was successful.
     *
     * @return {@code true} if the signup was successful, otherwise {@code false}.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the message associated with the signup process.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Compares this {@code UserSignupResponseModel} to another object for equality.
     *
     * @param o The object to compare to.
     * @return {@code true} if the objects are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSignupResponseModel that = (UserSignupResponseModel) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(success, message);
    }
}
