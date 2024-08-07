package interface_adapter.AddEvent;

import use_case.AddEventUseCase.AddEventOutputBoundaryInterface;
import use_case.AddEventUseCase.AddEventOutputData;

public class AddEventPresenter implements AddEventOutputBoundaryInterface {
    private AddEventViewModel addEventViewModel;

    public AddEventPresenter(AddEventViewModel addEventViewModel) {
        this.addEventViewModel = addEventViewModel;
    }

    public void prepareFailState(AddEventOutputData addEventOutputData) {
        boolean startEndError = addEventOutputData.getStartEndError();
        System.out.println(startEndError);
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
