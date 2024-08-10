package use_case.CalendarUseCases.ViewEventsUseCase;

import entity.CalendarEvent;

import java.time.LocalDateTime;
import java.util.List;

public class ViewEventsOutputData {
    private List<CalendarEvent> eventList;

    public ViewEventsOutputData(List<CalendarEvent> eventList) {
        this.eventList = eventList;
    }

    // Getter method
    public List<CalendarEvent> getEventList() {return this.eventList;}
}
