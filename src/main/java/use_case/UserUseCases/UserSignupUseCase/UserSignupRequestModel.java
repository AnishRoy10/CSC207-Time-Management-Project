package use_case.UserUseCases.UserSignupUseCase;

/**
 * Request model for the user signup use case.
 * This model encapsulates the data required for a user to sign up, including the username, password, and confirmation password.
 */
public class UserSignupRequestModel {
    private final String username;
    private final String password;
    private final String confirmPassword;

    /**
     * Constructs a {@code UserSignupRequestModel} with the specified username, password, and confirmation password.
     *
     * @param username        The username of the user.
     * @param password        The password of the user.
     * @param confirmPassword The confirmation of the user's password.
     */
    public UserSignupRequestModel(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the confirmation password of the user.
     *
     * @return The confirmation password.
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }
}
