package use_case.CalendarUseCases.AddEventUseCase;

import interface_adapter.AddEvent.AddEventPresenter;

import java.io.IOException;

/**
 * The interactor for the add event use case. Adds an event to the user's calendar and
 * lets the presenter know to begin the process of updating the view.
 */
public class AddEventUseCaseInteractor implements AddEventInputBoundary{
    private AddEventDataAccessInterface calendarDataAccessObject;
    private AddEventPresenter addEventPresenter;

    public AddEventUseCaseInteractor(AddEventDataAccessInterface calendarDataAccessObject,
                                     AddEventPresenter addEventPresenter) {
        this.calendarDataAccessObject = calendarDataAccessObject;
        this.addEventPresenter = addEventPresenter;
    }

    public void execute(AddEventInputData addEventInputData) throws IOException, ClassNotFoundException {
        boolean startEndError = !addEventInputData.getEventToBeAdded().startEndOnSameDay();
        boolean priorityLevelError = !addEventInputData.getEventToBeAdded().priorityLevelIsValid();
        boolean startAfterEndError = !addEventInputData.getEventToBeAdded().startBeforeEnd();

        if (!startEndError && !priorityLevelError && !startAfterEndError)
        {calendarDataAccessObject.addEvent(addEventInputData.getEventToBeAdded());}

        AddEventOutputData addEventOutputData = new AddEventOutputData(startEndError, priorityLevelError, startAfterEndError);

        addEventPresenter.prepareFailState(addEventOutputData);
    }

}
