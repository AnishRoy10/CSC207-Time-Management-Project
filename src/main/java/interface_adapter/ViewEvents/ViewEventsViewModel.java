package interface_adapter.ViewEvents;

import entity.CalendarEvent;

import java.util.ArrayList;
import java.util.List;

public class ViewEventsViewModel {
    private List<CalendarEvent> eventList;

    public ViewEventsViewModel() {
        eventList = new ArrayList<>();
    }

    public List<CalendarEvent> getEventListToBeShown() {return this.eventList;}

    public void setEventList(List<CalendarEvent> eventList) {this.eventList = eventList;}

}
