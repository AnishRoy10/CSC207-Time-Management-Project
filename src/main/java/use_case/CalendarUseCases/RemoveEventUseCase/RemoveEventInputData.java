package use_case.CalendarUseCases.RemoveEventUseCase;

import entity.CalendarEvent;

/**
 * Stores the event to be removed for the remove event use case
 */
public class RemoveEventInputData {
    private CalendarEvent event;

    public RemoveEventInputData(CalendarEvent event) {
        this.event = event;
    }

    public CalendarEvent getEvent() {
        return event;
    }
}
