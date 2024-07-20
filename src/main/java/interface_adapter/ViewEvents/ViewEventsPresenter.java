package interface_adapter.ViewEvents;

import entity.CalendarEvent;
import use_case.ViewEventsUseCase.ViewEventsOutputBoundary;
import use_case.ViewEventsUseCase.ViewEventsOutputData;

import java.util.List;

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
