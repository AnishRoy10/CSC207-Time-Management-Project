package interface_adapter.RemoveEvent;

import entity.CalendarEvent;
import use_case.CalendarUseCases.RemoveEventUseCase.RemoveEventInputBoundary;
import use_case.CalendarUseCases.RemoveEventUseCase.RemoveEventInputData;

import java.io.IOException;

/**
 * Controller for remove event use case. Sends the event to be deleted to the remove event
 * use case interactor via the execute button, storing it in an instance of the
 * RemoveEventInputData class
 */
public class RemoveEventController {
    private RemoveEventInputBoundary removeEventUseCaseInteractor;

    public RemoveEventController(RemoveEventInputBoundary removeEventUseCaseInteractor) {
        this.removeEventUseCaseInteractor = removeEventUseCaseInteractor;
    }

    public void execute(CalendarEvent event) throws IOException {
        RemoveEventInputData inputData = new RemoveEventInputData(event);
        removeEventUseCaseInteractor.execute(inputData);
    }
}
