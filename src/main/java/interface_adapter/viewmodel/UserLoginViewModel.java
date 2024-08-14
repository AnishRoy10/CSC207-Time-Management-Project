package interface_adapter.viewmodel;

/**
 * The UserLoginViewModel class serves as the ViewModel for the user login process.
 * It provides a structure to hold the message resulting from a login attempt, which
 * the user interface can then display to the user.
 */
public class UserLoginViewModel {
    private String message; // Message resulting from the login attempt, to be displayed to the user

    /**
     * Retrieves the message resulting from the login attempt.
     *
     * @return The login message as a string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message resulting from the login attempt.
     *
     * @param message The login message as a string.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
