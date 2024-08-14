package use_case.CalendarUseCases.AddEventUseCase;

import entity.CalendarEvent;

/**
 * Stores the event that is being added for the add event use case inside of it
 */
public class AddEventInputData {
    private CalendarEvent eventToBeAdded;

    public AddEventInputData(CalendarEvent eventToBeAdded) {this.eventToBeAdded = eventToBeAdded;}

    // Getter method for eventToBeAdded
    public CalendarEvent getEventToBeAdded() {return this.eventToBeAdded;}
}
