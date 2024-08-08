package use_case.CalendarUseCases.AddEventUseCase;

public class AddEventOutputData {
    private boolean startEndError;
    private boolean priorityLevelError;
    private boolean startAfterEndError;

    public AddEventOutputData(boolean startEndError, boolean priorityLevelError, boolean startAfterEndError) {
        this.startEndError = startEndError;
        this.priorityLevelError = priorityLevelError;
        this.startAfterEndError = startAfterEndError;
    }

    public boolean getStartEndError() {
        return startEndError;
    }

    public boolean getPriorityLevelError() {
        return priorityLevelError;
    }

    public boolean getStartAfterEndError() {
        return startAfterEndError;
    }
}
