package app.gui;

import data_access.SQLDatabaseHelper;
import data_access.UserDAO;
import data_access.TimerDataAccessObject;
import interface_adapter.presenter.TimerPresenter;
import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.controller.TimerController;
import framework.view.SetTimerView;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInteractor;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInteractor;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerInteractor;

/**
 * Class to initialize the set timer page and its related components.
 */
public class TimerInitializer {
    /**
     * The main method is the entry point of the timer page.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        try {
            // Initialize the database
            SQLDatabaseHelper dbHelper = new SQLDatabaseHelper();
            dbHelper.initializeDatabase();

            // Create the UserDAO with the initialized database helper
            UserDAO userDAO = new UserDAO(dbHelper);

            // Initialize the TimerDataAccessObject with the UserDAO
            TimerDataAccessObject dataAccessObject = new TimerDataAccessObject(userDAO);

            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");

            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor setTimerInteractor = new SetTimerInteractor(dataAccessObject, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(dataAccessObject, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(dataAccessObject, presenter);
            TimerController controller = new TimerController(setTimerInteractor, updateTimerInteractor,
                    pauseTimerInteractor);

            SetTimerView view = new SetTimerView(controller, setTimerViewModel, runningTimerViewModel);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing Timer");
        }
    }
}
