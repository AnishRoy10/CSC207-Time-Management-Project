package use_case.AddEventUseCase;

import java.io.IOException;

public class AddEventUseCaseInteractor implements AddEventInputBoundary{
    private AddEventDataAccessInterface calendarDataAccessObject;

    public AddEventUseCaseInteractor(AddEventDataAccessInterface calendarDataAccessObject) {
        this.calendarDataAccessObject = calendarDataAccessObject;
    }

    public void execute(AddEventInputData addEventInputData) throws IOException, ClassNotFoundException {
        calendarDataAccessObject.addEvent(addEventInputData.getEventToBeAdded());
    }

}
