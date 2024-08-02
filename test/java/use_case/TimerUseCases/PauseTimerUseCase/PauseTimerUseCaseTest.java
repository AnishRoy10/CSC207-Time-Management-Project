package use_case.TimerUseCases.PauseTimerUseCase;

import data_access.FileCacheUserDataAccessObject;
import data_access.TimerDataAccessObject;
import entity.Course;
import entity.Timer;
import entity.User;
import framework.view.RunningTimerView;
import interface_adapter.controller.TimerController;
import interface_adapter.presenter.TimerPresenter;
import interface_adapter.viewmodel.RunningTimerViewModel;
import interface_adapter.viewmodel.SetTimerViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInteractor;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerInteractor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * End-to-end tests for update timer use case.
 */
public class PauseTimerUseCaseTest {
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
    void testPauseTimerUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        Timer timer = new Timer(1, 0, 1);
        user.addTimer(timer);
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");
            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor interactor = new SetTimerInteractor(userRepository, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(userRepository, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(userRepository, presenter);
            TimerController controller = new TimerController(interactor, updateTimerInteractor, pauseTimerInteractor);
            RunningTimerView view = new RunningTimerView(controller, runningTimerViewModel);
            Method update_method = view.getClass().getDeclaredMethod("updateTimer");
            update_method.setAccessible(true);
            update_method.invoke(view);
            Method pause_method = view.getClass().getDeclaredMethod("pauseTimer");
            pause_method.setAccessible(true);

//            controller.execute_pause_timer(false);
            pause_method.invoke(view);
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 500) {
                update_method.invoke(view);

            }
            assertEquals("Resume", RunningTimerViewModel.PAUSE_LABEL);
            assertEquals("1", RunningTimerViewModel.HOURS);
            assertEquals("0", RunningTimerViewModel.MINUTES);
            assertEquals("0", RunningTimerViewModel.SECONDS);

            controller.execute_pause_timer(true);

            startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 1000) {
                controller.execute_update_timer();

            }

            assertEquals("Pause", RunningTimerViewModel.PAUSE_LABEL);
            assertEquals("0", RunningTimerViewModel.HOURS);
            assertEquals("59", RunningTimerViewModel.MINUTES);
            assertEquals("59", RunningTimerViewModel.SECONDS);
        });
    }
}
