package use_case.ViewEventsUseCase;

import entity.Calendar;
import entity.CalendarEvent;
import interface_adapter.ViewEvents.ViewEventsPresenter;

import java.io.IOException;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

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
