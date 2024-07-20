package app.gui;

import data_access.CalendarDataAccessObject;
import framework.view.CalendarView;
import interface_adapter.AddEvent.AddEventController;
import interface_adapter.ViewEvents.*;
import use_case.AddEventUseCase.AddEventUseCaseInteractor;
import use_case.ViewEventsUseCase.ViewEventsUseCaseInteractor;

import java.io.IOException;

public class CalendarInitializer{
    // Initializes a dataAccessObject for the calendar to use
    private static CalendarDataAccessObject calendarDataAccessObject;
    public CalendarInitializer() throws IOException {
        calendarDataAccessObject = new CalendarDataAccessObject();
    }
    public static void main(String[] args) {
        // Initialize the view models
        ViewEventsViewModel viewEventsViewModel = new ViewEventsViewModel();

        // Initialize the presenter
        ViewEventsPresenter viewEventsPresenter = new ViewEventsPresenter(viewEventsViewModel);

        // Initialize the use case interactors
        ViewEventsUseCaseInteractor viewEventsUseCaseInteractor =
                new ViewEventsUseCaseInteractor(calendarDataAccessObject, viewEventsPresenter);
        AddEventUseCaseInteractor addEventUseCaseInteractor = new AddEventUseCaseInteractor(calendarDataAccessObject);

        // Initialize the controllers
        ViewEventsController viewEventsController = new ViewEventsController(viewEventsUseCaseInteractor);
        AddEventController addEventController = new AddEventController(addEventUseCaseInteractor);
        CalendarView calendarView = new CalendarView(viewEventsViewModel, viewEventsController, addEventController);
        //

    }
}
