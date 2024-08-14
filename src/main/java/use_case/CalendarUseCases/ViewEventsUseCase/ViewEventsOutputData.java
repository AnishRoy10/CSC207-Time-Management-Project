package use_case.CalendarUseCases.ViewEventsUseCase;

import entity.CalendarEvent;
import java.util.List;

/**
 * Stores the up to date events on the user has on the day to be displayed to the user
 */
public class ViewEventsOutputData {
    private List<CalendarEvent> eventList;

    public ViewEventsOutputData(List<CalendarEvent> eventList) {
        this.eventList = eventList;
    }

    // Getter method
    public List<CalendarEvent> getEventList() {return this.eventList;}
}
