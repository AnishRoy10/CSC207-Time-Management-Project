package app.gui;

import data_access.CalendarDataAccessObject;
import framework.view.CalendarView;
import interface_adapter.AddEvent.AddEventController;
import interface_adapter.ViewEvents.*;
import use_case.AddEventUseCase.AddEventUseCaseInteractor;
import use_case.ViewEventsUseCase.ViewEventsUseCaseInteractor;

import java.io.IOException;

public class CalendarInitializer{
    public static void main(String[] args) {
        try {
            // Initialize the view models
            ViewEventsViewModel viewEventsViewModel = new ViewEventsViewModel();

            // Initialize the presenter
            ViewEventsPresenter viewEventsPresenter = new ViewEventsPresenter(viewEventsViewModel);

            // Initialize the use case interactors
            CalendarDataAccessObject calendarDataAccessObject = new CalendarDataAccessObject();
            ViewEventsUseCaseInteractor viewEventsUseCaseInteractor =
                    new ViewEventsUseCaseInteractor(calendarDataAccessObject, viewEventsPresenter);
            AddEventUseCaseInteractor addEventUseCaseInteractor = new AddEventUseCaseInteractor(calendarDataAccessObject);

            // Initialize the controllers
            ViewEventsController viewEventsController = new ViewEventsController(viewEventsUseCaseInteractor);
            AddEventController addEventController = new AddEventController(addEventUseCaseInteractor);

            CalendarView calendarView = new CalendarView(viewEventsViewModel, viewEventsController, addEventController);
        }
        catch(IOException e) {System.out.println("Could Not Initialize a CalendarDataAccessObject");}
    }
}
