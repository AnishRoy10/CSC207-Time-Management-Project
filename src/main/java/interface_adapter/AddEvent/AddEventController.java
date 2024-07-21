package interface_adapter.AddEvent;

import entity.CalendarEvent;
import use_case.AddEventUseCase.AddEventInputData;
import use_case.AddEventUseCase.AddEventUseCaseInteractor;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Controller for the AddEvent use case. Turns the input data from the view into an
 * event object that the interactor more easily makes use of.
 */
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
