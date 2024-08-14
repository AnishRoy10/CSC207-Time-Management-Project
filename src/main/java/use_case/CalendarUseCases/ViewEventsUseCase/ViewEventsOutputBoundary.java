package use_case.CalendarUseCases.ViewEventsUseCase;

/**
 * Interface for the presenter of view events use case. Must have a prepareEventView method which
 * lets the view model know which events the user now has on the day chosen to be viewed.
 */
public interface ViewEventsOutputBoundary {
    void prepareEventView(ViewEventsOutputData viewEventsOutputData);
}
