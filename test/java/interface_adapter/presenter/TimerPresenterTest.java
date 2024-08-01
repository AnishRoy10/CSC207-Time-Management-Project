package interface_adapter.presenter;

import interface_adapter.viewmodel.RunningTimerViewModel;
import interface_adapter.viewmodel.SetTimerViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerOutputData;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerOutputData;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimerPresenterTest {
    private TimerPresenter presenter;
    private SetTimerViewModel setTimerViewModel;
    private RunningTimerViewModel runningTimerViewModel;

    @BeforeEach
    public void setUp() {
        setTimerViewModel = new SetTimerViewModel("set timer");
        runningTimerViewModel = new RunningTimerViewModel("running timer");
        presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
    }

    @Test
    public void testPresentSetTimer() {
        SetTimerOutputData setTimerOutputData = new SetTimerOutputData(1, 0, 0);

        presenter.prepareSuccessView(setTimerOutputData);

        assertEquals("1", RunningTimerViewModel.HOURS);
        assertEquals("0", RunningTimerViewModel.MINUTES);
        assertEquals("0", RunningTimerViewModel.SECONDS);
    }

    @Test
    public void testPresentSetTimerFail() {
        presenter.prepareFailView("Fail");

        assertEquals("Fail", runningTimerViewModel.getMessage());
    }

    @Test
    public void testPresentUpdateTimer() {
        UpdateTimerOutputData updateTimerOutputData = new UpdateTimerOutputData(1, 2, 3);

        presenter.prepareSuccessView(updateTimerOutputData);

        assertEquals("1", RunningTimerViewModel.HOURS);
        assertEquals("2", RunningTimerViewModel.MINUTES);
        assertEquals("3", RunningTimerViewModel.SECONDS);
    }

    @Test
    public void testPresentPauseTimer() {
        PauseTimerOutputData pauseTimerOutputData = new PauseTimerOutputData(true);

        presenter.prepareSuccessView(pauseTimerOutputData);

        assertEquals("Resume", RunningTimerViewModel.PAUSE_LABEL);

        PauseTimerOutputData pauseTimerOutputData2 = new PauseTimerOutputData(false);

        presenter.prepareSuccessView(pauseTimerOutputData2);

        assertEquals("Pause", RunningTimerViewModel.PAUSE_LABEL);
    }
}
