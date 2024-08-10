package use_case.CalendarUseCases.AddEventUseCase;

import entity.CalendarEvent;

public class AddEventInputData {
    private CalendarEvent eventToBeAdded;

    public AddEventInputData(CalendarEvent eventToBeAdded) {this.eventToBeAdded = eventToBeAdded;}

    // Getter method for eventToBeAdded
    public CalendarEvent getEventToBeAdded() {return this.eventToBeAdded;}
}
