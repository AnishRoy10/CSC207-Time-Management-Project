package use_case.UserSignupUseCase;

/**
 * Request model for user sign-up use case.
 */
public class UserSignupRequestModel {
    private final String username;
    private final String password;

    /**
     * Constructs a UserSignupRequestModel with the specified username and password.
     *
     * @param username  The username of the user.
     * @param password  The password of the user.
     */
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
