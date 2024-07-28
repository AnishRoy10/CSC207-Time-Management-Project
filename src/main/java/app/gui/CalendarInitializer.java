package app.gui;

import data_access.CalendarDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import framework.view.CalendarView;
import interface_adapter.AddEvent.AddEventController;
import interface_adapter.AddEvent.AddEventPresenter;
import interface_adapter.AddEvent.AddEventViewModel;
import interface_adapter.ViewEvents.*;
import use_case.AddEventUseCase.AddEventUseCaseInteractor;
import use_case.ViewEventsUseCase.ViewEventsUseCaseInteractor;

import java.io.IOException;

public class CalendarInitializer{
    public static void initializeCalendar(String username) {
        try {
            // Initialize the view models
            ViewEventsViewModel viewEventsViewModel = new ViewEventsViewModel();
            AddEventViewModel addEventViewModel = new AddEventViewModel();

            // Initialize the presenter
            ViewEventsPresenter viewEventsPresenter = new ViewEventsPresenter(viewEventsViewModel);
            AddEventPresenter addEventPresenter = new AddEventPresenter(addEventViewModel);

            // Initialize the use case interactors
            CalendarDataAccessObject calendarDataAccessObject = new CalendarDataAccessObject(username);
            ViewEventsUseCaseInteractor viewEventsUseCaseInteractor =
                    new ViewEventsUseCaseInteractor(calendarDataAccessObject, viewEventsPresenter);
            AddEventUseCaseInteractor addEventUseCaseInteractor =
                    new AddEventUseCaseInteractor(calendarDataAccessObject, addEventPresenter);

            // Initialize the controllers
            ViewEventsController viewEventsController = new ViewEventsController(viewEventsUseCaseInteractor);
            AddEventController addEventController = new AddEventController(addEventUseCaseInteractor);

            CalendarView calendarView = new CalendarView(viewEventsViewModel, viewEventsController, addEventController, addEventViewModel);
        } catch (IOException e) {
            System.out.println("Could Not Initialize a CalendarDataAccessObject");
        }
    }
}
