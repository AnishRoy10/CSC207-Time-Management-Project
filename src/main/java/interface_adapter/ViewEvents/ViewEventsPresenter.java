package interface_adapter.ViewEvents;

import entity.CalendarEvent;
import use_case.ViewEventsUseCase.ViewEventsOutputBoundary;
import use_case.ViewEventsUseCase.ViewEventsOutputData;

import java.util.List;

/**
 * The presenter for the view events use case. Simply passes the output data from the
 * interactor to the view model as a list of events to be displayed on the view.
 */
public class ViewEventsPresenter implements ViewEventsOutputBoundary {
    private ViewEventsViewModel viewEventsViewModel;

    public ViewEventsPresenter(ViewEventsViewModel viewEventsViewModel) {
        this.viewEventsViewModel = viewEventsViewModel;
    }
    public void prepareEventView(ViewEventsOutputData viewEventsOutputData) {
        List<CalendarEvent> eventsToBeShown = viewEventsOutputData.getEventList();
        viewEventsViewModel.setEventList(eventsToBeShown);
    }
}
