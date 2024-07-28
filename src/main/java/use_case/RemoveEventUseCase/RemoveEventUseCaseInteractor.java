package use_case.RemoveEventUseCase;

import java.io.IOException;

public class RemoveEventUseCaseInteractor implements RemoveEventInputBoundary{
    private RemoveEventDataAccessInterface calendarDataAccessObject;

    public RemoveEventUseCaseInteractor(RemoveEventDataAccessInterface calendarDataAccessObject) throws IOException {
        this.calendarDataAccessObject = calendarDataAccessObject;
    }

    public void execute(RemoveEventInputData removeEventInputData) throws IOException {
        calendarDataAccessObject.removeEvent(removeEventInputData.getEvent());
    }
}
