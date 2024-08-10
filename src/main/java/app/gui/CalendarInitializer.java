package app.gui;

import data_access.CalendarDataAccessObject;
import data_access.SQLDatabaseHelper;
import data_access.UserDAO;
import framework.view.CalendarView;
import interface_adapter.AddEvent.AddEventController;
import interface_adapter.AddEvent.AddEventPresenter;
import interface_adapter.AddEvent.AddEventViewModel;
import interface_adapter.RemoveEvent.RemoveEventController;
import interface_adapter.ViewEvents.ViewEventsController;
import interface_adapter.ViewEvents.ViewEventsPresenter;
import interface_adapter.ViewEvents.ViewEventsViewModel;
import use_case.CalendarUseCases.AddEventUseCase.AddEventUseCaseInteractor;
import use_case.CalendarUseCases.RemoveEventUseCase.RemoveEventUseCaseInteractor;
import use_case.CalendarUseCases.ViewEventsUseCase.ViewEventsUseCaseInteractor;

import java.io.IOException;

public class CalendarInitializer {
    public static void initializeCalendar(String username) {
        try {
            // Initialize the database helper
            SQLDatabaseHelper dbHelper = new SQLDatabaseHelper();
            dbHelper.initializeDatabase();

            // Initialize the DAO
            UserDAO userDAO = new UserDAO(dbHelper);
            CalendarDataAccessObject calendarDataAccessObject = new CalendarDataAccessObject(username, dbHelper);

            // Initialize the view models
            ViewEventsViewModel viewEventsViewModel = new ViewEventsViewModel();
            AddEventViewModel addEventViewModel = new AddEventViewModel();

            // Initialize the presenters
            ViewEventsPresenter viewEventsPresenter = new ViewEventsPresenter(viewEventsViewModel);
            AddEventPresenter addEventPresenter = new AddEventPresenter(addEventViewModel);

            // Initialize the use case interactors
            ViewEventsUseCaseInteractor viewEventsUseCaseInteractor = new ViewEventsUseCaseInteractor(calendarDataAccessObject, viewEventsPresenter);
            AddEventUseCaseInteractor addEventUseCaseInteractor = new AddEventUseCaseInteractor(calendarDataAccessObject, addEventPresenter);
            RemoveEventUseCaseInteractor removeEventUseCaseInteractor = new RemoveEventUseCaseInteractor(calendarDataAccessObject);

            // Initialize the controllers
            ViewEventsController viewEventsController = new ViewEventsController(viewEventsUseCaseInteractor);
            AddEventController addEventController = new AddEventController(addEventUseCaseInteractor);
            RemoveEventController removeEventController = new RemoveEventController(removeEventUseCaseInteractor);

            // Initialize the calendar view
            CalendarView calendarView = new CalendarView(viewEventsViewModel, viewEventsController, addEventController, addEventViewModel, removeEventController);
        } catch (IOException e) {
            System.out.println("Could Not Initialize a CalendarDataAccessObject");
        }
    }
}
