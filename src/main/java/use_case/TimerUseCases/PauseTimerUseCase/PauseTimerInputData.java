package use_case.TimerUseCases.PauseTimerUseCase;

public class PauseTimerInputData {
    final private boolean paused;

    public PauseTimerInputData(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
