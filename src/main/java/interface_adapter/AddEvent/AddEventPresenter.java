package interface_adapter.AddEvent;

import use_case.CalendarUseCases.AddEventUseCase.AddEventOutputBoundaryInterface;
import use_case.CalendarUseCases.AddEventUseCase.AddEventOutputData;

/**
 * Presenter for the add event use case.
 * Displays the appropriate error window if user does not input valid
 * information when attempting to add an event
 */
public class AddEventPresenter implements AddEventOutputBoundaryInterface {
    private AddEventViewModel addEventViewModel;

    public AddEventPresenter(AddEventViewModel addEventViewModel) {
        this.addEventViewModel = addEventViewModel;
    }

    public void prepareFailState(AddEventOutputData addEventOutputData) {
        boolean startEndError = addEventOutputData.getStartEndError();
        boolean priorityLevelError = addEventOutputData.getPriorityLevelError();
        boolean startAfterEndError = addEventOutputData.getStartAfterEndError();

        if (startEndError) {addEventViewModel.setStartEndError(true);}
        else {addEventViewModel.setStartEndError(false);}

        if (priorityLevelError) {addEventViewModel.setPriorityLevelError(true);}
        else {addEventViewModel.setPriorityLevelError(false);}

        if (startAfterEndError) {addEventViewModel.setStartAfterEndError(true);}
        else {addEventViewModel.setStartAfterEndError(false);}
    }
}
