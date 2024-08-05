package use_case.TimerUseCases.PauseTimerUseCase;

/**
 * Class representing the input data for the pause timer use case.
 */
public class PauseTimerInputData {
    final private boolean paused;

    /**
     * Constructor for PauseTimerInputData
     * @param paused boolean representing whether the timer is paused
     */
    public PauseTimerInputData(boolean paused) {
        this.paused = paused;
    }

    /**
     * Getter method for paused.
     * @return boolean representing whether the timer is paused
     */
    public boolean isPaused() {
        return paused;
    }
}
