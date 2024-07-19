package use_case.UserUseCases.UserLoginUseCase;

/**
 * Request model for the user login use case.
 */
public class UserLoginRequestModel {
    private final String username;
    private final String password;

    /**
     * Constructs a UserLoginRequestModel with the specified username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public UserLoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }
}
