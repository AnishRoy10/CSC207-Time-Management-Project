package use_case.TimerUseCases.PauseTimerUseCase;

/**
 * Class representing the output data for the pause timer use case.
 */
public class PauseTimerOutputData {

    private final boolean paused;

    /**
     * Constructor for PauseTimerOutputData.
     * @param paused boolean representing whether the timer has changed to being paused or not
     */
    public PauseTimerOutputData(boolean paused) {
        this.paused = paused;
    }

    /**
     * Getter for paused.
     * @return boolean representing whether the timer has changed to being paused or not
     */
    public boolean isPaused() {
        return paused;
    }
}
