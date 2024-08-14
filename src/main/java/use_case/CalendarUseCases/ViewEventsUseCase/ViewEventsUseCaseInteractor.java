package use_case.CalendarUseCases.ViewEventsUseCase;

import entity.Calendar;
import entity.CalendarEvent;
import interface_adapter.ViewEvents.ViewEventsPresenter;

import java.io.IOException;
import java.time.LocalDateTime;

import java.util.List;

/**
 * Interactor for the view events use case. Passes the events the user has on the day that they
 * chose to the presenter by calling appropriate data access objects and via the controller
 */
public class ViewEventsUseCaseInteractor implements ViewEventsInputBoundary{
    private ViewEventsDataAccessInterface calendarDataAccessObject;
    private ViewEventsPresenter viewEventsPresenter;
    public ViewEventsUseCaseInteractor(ViewEventsDataAccessInterface calendarDataAccessObject,
                                       ViewEventsPresenter viewEventsPresenter){
        this.calendarDataAccessObject = calendarDataAccessObject;
        this.viewEventsPresenter = viewEventsPresenter;
    }
    public void execute(ViewEventsInputData viewEventsInputData) throws IOException, ClassNotFoundException{
        Calendar calendar = calendarDataAccessObject.getCalendar();
        LocalDateTime start = viewEventsInputData.getStart();
        LocalDateTime end = viewEventsInputData.getEnd();
        List<CalendarEvent> eventsBetweenDates = calendar.eventsBetweenDates(start, end);
        ViewEventsOutputData viewEventsOutputData = new ViewEventsOutputData(eventsBetweenDates);
        viewEventsPresenter.prepareEventView(viewEventsOutputData);
    }
}
