package use_case.TimerUseCases.SetTimerUseCase;

import data_access.FileCacheUserDataAccessObject;
import data_access.TimerDataAccessObject;
import entity.Course;
import entity.User;
import interface_adapter.controller.TimerController;
import interface_adapter.presenter.TimerPresenter;
import interface_adapter.viewmodel.RunningTimerViewModel;
import interface_adapter.viewmodel.SetTimerViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInteractor;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerInteractor;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end tests for set timer use case.
 */
public class SetTimerUseCaseTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "test_userCache.json";
    private TimerDataAccessObject userRepository;

    @BeforeEach
    public void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
        userRepository = new TimerDataAccessObject(fileCacheUserDAO);

    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testSetTimerSuccess() {
        // Test successful initialization of the timer.
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");
            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor interactor = new SetTimerInteractor(userRepository, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(userRepository, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(userRepository, presenter);
            TimerController controller = new TimerController(interactor, updateTimerInteractor, pauseTimerInteractor);
            controller.execute_set_timer("1", "0", "0");

            assertEquals("1", RunningTimerViewModel.HOURS);
            assertEquals("0", RunningTimerViewModel.MINUTES);
            assertEquals("0", RunningTimerViewModel.SECONDS);
        });
    }

    @Test
    public void testSetTimerZeroInput() {
        // Test when inputs are all zero.
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");
            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor interactor = new SetTimerInteractor(userRepository, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(userRepository, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(userRepository, presenter);
            TimerController controller = new TimerController(interactor, updateTimerInteractor, pauseTimerInteractor);
            controller.execute_set_timer("0", "0", "0");

            assertNull(RunningTimerViewModel.HOURS);
            assertNull(RunningTimerViewModel.MINUTES);
            assertNull(RunningTimerViewModel.SECONDS);
        });
    }

    @Test
    public void testSetTimerOverSixtyInput(){
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");
            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor interactor = new SetTimerInteractor(userRepository, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(userRepository, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(userRepository, presenter);
            TimerController controller = new TimerController(interactor, updateTimerInteractor, pauseTimerInteractor);
            controller.execute_set_timer("1", "61", "0");

            assertEquals("2", RunningTimerViewModel.HOURS);
            assertEquals("1", RunningTimerViewModel.MINUTES);
            assertEquals("0", RunningTimerViewModel.SECONDS);
        });
    }

    @Test
    public void testSetTimerNonNumericInput() {
        // Test when inputs are all zero.
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");
            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor interactor = new SetTimerInteractor(userRepository, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(userRepository, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(userRepository, presenter);
            TimerController controller = new TimerController(interactor, updateTimerInteractor, pauseTimerInteractor);
            controller.execute_set_timer("a", "0", "1");

            assertNull(RunningTimerViewModel.HOURS);
            assertNull(RunningTimerViewModel.MINUTES);
            assertNull(RunningTimerViewModel.SECONDS);
        });
    }
}