package use_case.TimerUseCases.PauseTimerUseCase;

public class PauseTimerOutputData {

    private final boolean paused;

    public PauseTimerOutputData(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
