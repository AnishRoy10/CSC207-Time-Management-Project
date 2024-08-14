package use_case.CalendarUseCases.RemoveEventUseCase;

import java.io.IOException;

/**
 * Use case interactor for the remove event use case. Removes the event from the user's
 * calendar using the appropriate data access objects. Does not need to alert view directly.
 */
public class RemoveEventUseCaseInteractor implements RemoveEventInputBoundary{
    private RemoveEventDataAccessInterface calendarDataAccessObject;

    public RemoveEventUseCaseInteractor(RemoveEventDataAccessInterface calendarDataAccessObject) throws IOException {
        this.calendarDataAccessObject = calendarDataAccessObject;
    }

    public void execute(RemoveEventInputData removeEventInputData) throws IOException {
        calendarDataAccessObject.removeEvent(removeEventInputData.getEvent());
    }
}
