package interface_adapter.viewmodel;

/**
 * The UserSignupViewModel class serves as the ViewModel for the user signup process.
 * It provides a structure to hold both the success status and the message resulting
 * from a signup attempt, which the user interface can then display to the user.
 */
public class UserSignupViewModel {
    private boolean signupSuccess; // Indicates whether the signup was successful
    private String message; // Message resulting from the signup attempt, to be displayed to the user

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
     * Retrieves the message resulting from the signup attempt.
     *
     * @return The signup message as a string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message resulting from the signup attempt.
     *
     * @param message The signup message as a string.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
