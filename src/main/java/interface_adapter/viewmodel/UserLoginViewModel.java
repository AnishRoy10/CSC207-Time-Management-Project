package interface_adapter.viewmodel;

/**
 * ViewModel class for user login.
 */
public class UserLoginViewModel {
    private String message;

    /**
     * Gets the login message.
     *
     * @return The login message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the login message.
     *
     * @param message The login message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
