package use_case.UserSignupUseCase;

/**
 * Request model for user sign-up use case.
 */
public class UserSignupRequestModel {
    private final String username;
    private final String password;
    private final String confirmPassword;

    /**
     * Constructs a UserSignupRequestModel with the specified username and password.
     *
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param confirmPassword The password of the user again, to confirm if the input is the same.
     */
    public UserSignupRequestModel(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
