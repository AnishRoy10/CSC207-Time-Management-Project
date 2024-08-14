package interface_adapter.AddEvent;

/**
 * The view accesses the view model's getter method to determine which error window
 * to display or whether to display one at all
 */
public class AddEventViewModel {
    private boolean startEndError = false;
    private boolean priorityLevelError = false;
    private boolean startAfterEndError = false;

    public boolean getStartEndError() {
        return startEndError;
    }

    public boolean getPriorityLevelError() {
        return priorityLevelError;
    }

    public boolean getStartAfterEndError() {
        return startAfterEndError;
    }

    public void setStartEndError(boolean startEndError) {
        this.startEndError = startEndError;
    }
    public void setPriorityLevelError(boolean priorityLevelError) {
        this.priorityLevelError = priorityLevelError;
    }
    public void setStartAfterEndError(boolean startAfterEndError) {
        this.startAfterEndError = startAfterEndError;
    }
}
