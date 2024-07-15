package interface_adapter.viewmodel;

/**
 * View model for user signup.
 */
public class UserSignupViewModel {
    private boolean signupSuccess;
    private String message;

    /**
     * Checks if the signup was successful.
     *
     * @return True if the signup was successful, otherwise false.
     */
    public boolean isSignupSuccess() {
        return signupSuccess;
    }

    /**
     * Sets the signup success status.
     *
     * @param signupSuccess True if the signup was successful, otherwise false.
     */
    public void setSignupSuccess(boolean signupSuccess) {
        this.signupSuccess = signupSuccess;
    }

    /**
     * Gets the signup message.
     *
     * @return The signup message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the signup message.
     *
     * @param message The signup message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
