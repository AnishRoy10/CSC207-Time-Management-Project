package interface_adapter.ViewEvents;

import entity.CalendarEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the view events use case. It stores the events to be shown from a certain day on the
 * user's calendar, and passes this list to the view, where they are then displayed.
 */
public class ViewEventsViewModel {
    private List<CalendarEvent> eventList;

    public ViewEventsViewModel() {
        eventList = new ArrayList<>();
    }

    public List<CalendarEvent> getEventListToBeShown() {return this.eventList;}

    public void setEventList(List<CalendarEvent> eventList) {this.eventList = eventList;}

}
