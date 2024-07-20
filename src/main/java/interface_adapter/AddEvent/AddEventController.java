package interface_adapter.AddEvent;

import entity.CalendarEvent;
import use_case.AddEventUseCase.AddEventInputData;
import use_case.AddEventUseCase.AddEventUseCaseInteractor;

import java.io.IOException;
import java.time.LocalDateTime;

public class AddEventController {
    private AddEventUseCaseInteractor addEventUseCaseInteractor;

    public AddEventController(AddEventUseCaseInteractor addEventUseCaseInteractor) {
        this.addEventUseCaseInteractor = addEventUseCaseInteractor;
    }

    public void execute(
            String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String priorityLevel) throws IOException, ClassNotFoundException {
        CalendarEvent eventToBeAdded = new CalendarEvent(name, description, priorityLevel, startDate, endDate);
        AddEventInputData addEventInputData = new AddEventInputData(eventToBeAdded);
        addEventUseCaseInteractor.execute(addEventInputData);
    }

}
