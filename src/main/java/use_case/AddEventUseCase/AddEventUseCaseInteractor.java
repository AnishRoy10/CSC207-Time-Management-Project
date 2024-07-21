package use_case.AddEventUseCase;

import data_access.FileCacheUserDataAccessObject;
import entity.Calendar;
import entity.CalendarEvent;
import entity.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

public class AddEventUseCaseInteractor implements AddEventInputBoundary{
    private AddEventDataAccessInterface calendarDataAccessObject;

    public AddEventUseCaseInteractor(AddEventDataAccessInterface calendarDataAccessObject) {
        this.calendarDataAccessObject = calendarDataAccessObject;
    }

    public void execute(AddEventInputData addEventInputData) throws IOException, ClassNotFoundException {
        calendarDataAccessObject.addEvent(addEventInputData.getEventToBeAdded());
    }

}
