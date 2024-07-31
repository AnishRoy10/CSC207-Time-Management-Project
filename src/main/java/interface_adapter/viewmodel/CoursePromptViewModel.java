package interface_adapter.viewmodel;

/**
 * View model for all prompt-based course use cases.
 */
public class CoursePromptViewModel {
    // The response associated with the use case.
    private boolean response;

    // The response message associated with the use case.
    private String message;

    /**
     * Get the response embedded in this view model.
     * @return the boolean response
     */
    public boolean getResponse() {
        return response;
    }

    /**
     * Modify the response embedded in this view model.
     * @param response the new response value
     */
    public void setResponse(boolean response) {
        this.response = response;
    }

    /**
     * Get the message embedded in this view model.
     * @return the message response
     */
    public String getMessage() {
        return message;
    }

    /**
     * Modify the message embedded in this view model.
     * @param message the new message value
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
