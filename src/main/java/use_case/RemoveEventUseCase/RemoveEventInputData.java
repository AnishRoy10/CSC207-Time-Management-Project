package use_case.RemoveEventUseCase;

import entity.CalendarEvent;

public class RemoveEventInputData {
    private CalendarEvent event;

    public RemoveEventInputData(CalendarEvent event) {
        this.event = event;
    }

    public CalendarEvent getEvent() {
        return event;
    }
}
