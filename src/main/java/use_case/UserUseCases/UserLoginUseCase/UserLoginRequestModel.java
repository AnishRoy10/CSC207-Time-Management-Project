package use_case.UserUseCases.UserLoginUseCase;

/**
 * Request model for the user login use case.
 * This model encapsulates the data required for a user to log in, including the username and password.
 */
public class UserLoginRequestModel {
    private final String username;
    private final String password;

    /**
     * Constructs a {@code UserLoginRequestModel} with the specified username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     */
    public UserLoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
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
}
