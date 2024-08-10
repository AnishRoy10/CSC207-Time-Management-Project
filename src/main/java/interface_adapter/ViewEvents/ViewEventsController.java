package interface_adapter.ViewEvents;
import use_case.CalendarUseCases.ViewEventsUseCase.ViewEventsInputData;
import use_case.CalendarUseCases.ViewEventsUseCase.ViewEventsUseCaseInteractor;

import java.io.IOException;
import java.time.*;

/**
 * The controller for the view events use case. It takes the date one wants to view
 * the events on as input from the CalendarView and converts this into LocalDateTime
 * objects which the interactor can work with more easily, representing the start
 * and end of the day one wants to view events on respectively.
 */
public class ViewEventsController {

    private ViewEventsUseCaseInteractor viewEventsUseCaseInteractor;

    public ViewEventsController(ViewEventsUseCaseInteractor viewEventsUseCaseInteractor){
        this.viewEventsUseCaseInteractor = viewEventsUseCaseInteractor;
    }

    public void execute(LocalDate date) throws IOException, ClassNotFoundException {
        LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                0, 0);
        LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                23, 59);
        ViewEventsInputData viewEventsInputData = new ViewEventsInputData(start, end);
        viewEventsUseCaseInteractor.execute(viewEventsInputData);
    }
}
