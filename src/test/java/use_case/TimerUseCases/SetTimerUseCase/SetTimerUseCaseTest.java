package use_case.TimerUseCases.SetTimerUseCase;

import data_access.FileCacheUserDataAccessObject;
import data_access.TimerDataAccessObject;
import entity.Course;
import entity.User;
import interface_adapter.presenter.TimerPresenter;
import interface_adapter.viewmodel.RunningTimerViewModel;
import interface_adapter.viewmodel.SetTimerViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.UserRepository;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SetTimerUseCaseTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "test_userCache.json";
    private SetTimerDataAccessInterface userRepository;
//    private SetTimerOutputBoundary setTimerOutputBoundary;
//    private SetTimerInteractor setTimerInteractor;

    @BeforeEach
    public void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
        userRepository = new TimerDataAccessObject(fileCacheUserDAO);

//        setTimerDataAccessInterface = mock(SetTimerDataAccessInterface.class);
//        setTimerOutputBoundary = mock(SetTimerOutputBoundary.class);
//        setTimerInteractor = new SetTimerInteractor(setTimerDataAccessInterface, setTimerOutputBoundary);
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

            SetTimerInputData input = new SetTimerInputData(1, 0, 0);

            interactor.execute(input);


        });
    }

    @Test
    public void testSetTimerZeroInput() {
        // Test when inputs are all zero.
    }
}
