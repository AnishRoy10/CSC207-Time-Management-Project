package interface_adapter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInputBoundary;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInputData;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInputBoundary;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInputData;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerInputBoundary;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;

public class TimerControllerTest {
    private TimerController controller;
    private SetTimerInputBoundary setTimerInteractor;
    private UpdateTimerInputBoundary updateTimerInteractor;
    private PauseTimerInputBoundary pauseTimerInteractor;

    @BeforeEach
    public void setUp() {
        setTimerInteractor = mock(SetTimerInputBoundary.class);
        updateTimerInteractor = mock(UpdateTimerInputBoundary.class);
        pauseTimerInteractor = mock(PauseTimerInputBoundary.class);

        controller = new TimerController(setTimerInteractor,
                updateTimerInteractor, pauseTimerInteractor);
    }

    @Test
    public void setTimer() {
        String hours = "1";
        String minutes = "1";
        String seconds = "1";

        SetTimerInputData expectedInputData = new SetTimerInputData(1, 1, 1);

        controller.execute_set_timer(hours, minutes, seconds);

        verify(setTimerInteractor).execute(argThat(setTimerInputData ->
                setTimerInputData.getHours() == expectedInputData.getHours() &&
                setTimerInputData.getMinutes() == expectedInputData.getMinutes() &&
                setTimerInputData.getSeconds() == expectedInputData.getSeconds()
        ));
    }

    @Test
    public void setTimerInvalid() {
        String in = "a";

        SetTimerInputData expectedInputData = new SetTimerInputData(0, 0, 0);

        controller.execute_set_timer(in, in, in);

        verify(setTimerInteractor).execute(argThat(setTimerInputData ->
                setTimerInputData.getHours() == expectedInputData.getHours() &&
                        setTimerInputData.getMinutes() == expectedInputData.getMinutes() &&
                        setTimerInputData.getSeconds() == expectedInputData.getSeconds()
        ));

    }

    @Test
    public void pauseTimer() {
        boolean paused = false;

        PauseTimerInputData expectedInputData = new PauseTimerInputData(paused);

        controller.execute_pause_timer(paused);

        verify(pauseTimerInteractor).execute(argThat(pauseTimerInputData ->
                pauseTimerInputData.isPaused() == expectedInputData.isPaused()
        ));
    }
}
