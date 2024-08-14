package use_case.CalendarUseCases.AddEventUseCase;

/**
 * Interface requiring the presenter for add event use case to have a
 * prepareFailState method which lets the view model know whether or not the user incorrectly
 * added an event
 */
public interface AddEventOutputBoundaryInterface {
    void prepareFailState(AddEventOutputData addEventOutputData);

}
