package use_case.UserUseCases.UserSignupUseCase;

import java.util.Objects;

/**
 * Response model for the user signup use case.
 */
public class UserSignupResponseModel {
    private final boolean success;
    private final String message;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSignupResponseModel that = (UserSignupResponseModel) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message);
    }
}
