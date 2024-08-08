package use_case.TimerUseCases.UpdateTimerUseCase;

import data_access.SQLDatabaseHelper;
import data_access.UserDAO;
import data_access.TimerDataAccessObject;
import entity.Course;
import entity.Timer;
import entity.User;
import interface_adapter.controller.TimerController;
import interface_adapter.presenter.TimerPresenter;
import interface_adapter.viewmodel.RunningTimerViewModel;
import interface_adapter.viewmodel.SetTimerViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInteractor;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInteractor;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * End-to-end tests for update timer use case.
 */
public class UpdateTimerUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private UserDAO userDAO;
    private TimerDataAccessObject userRepository;

    @BeforeEach
    public void setUp() {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:test.db");
        dbHelper.initializeDatabase();
        userDAO = new UserDAO(dbHelper);
        userRepository = new TimerDataAccessObject(userDAO);
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File("test.db");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testUpdateTimerUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        Timer timer = new Timer(1, 0, 0);
        user.addTimer(timer);
        assertDoesNotThrow(() -> {
            userDAO.WriteToCache(user);
            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");
            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor interactor = new SetTimerInteractor(userRepository, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(userRepository, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(userRepository, presenter);
            TimerController controller = new TimerController(interactor, updateTimerInteractor, pauseTimerInteractor);

            long startTime = System.currentTimeMillis();
            long seconds = 1900;
            while (System.currentTimeMillis() - startTime < seconds) {
                controller.execute_update_timer();
            }
            assertEquals("0", RunningTimerViewModel.HOURS);
            assertEquals("59", RunningTimerViewModel.MINUTES);
            assertEquals("58", RunningTimerViewModel.SECONDS);
        });
    }
}
