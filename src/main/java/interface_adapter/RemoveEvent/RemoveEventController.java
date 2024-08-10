package interface_adapter.RemoveEvent;

import entity.CalendarEvent;
import use_case.CalendarUseCases.RemoveEventUseCase.RemoveEventInputBoundary;
import use_case.CalendarUseCases.RemoveEventUseCase.RemoveEventInputData;

import java.io.IOException;

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
