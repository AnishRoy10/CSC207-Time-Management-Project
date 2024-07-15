package use_case.UserSignUpUseCase;

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
}
