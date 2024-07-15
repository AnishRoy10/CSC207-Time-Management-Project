package use_case.UserSignUpUseCase;

public class UserSignupRequestModel {
    private final String username;
    private final String password;

    public UserSignupRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
