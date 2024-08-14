package use_case.TimerUseCases.SetTimerUseCase;

/**
 * Class representing the output data for the pause timer use case.
 */
public class SetTimerOutputData {
    private final int hours;
    private final int minutes;
    private final int seconds;

    /**
     * Constructor for SetTimerOutputData.
     * @param hours int representing hours the timer was set for
     * @param minutes int representing minutes the timer was set for
     * @param seconds int representing seconds the timer was set for
     */
    public SetTimerOutputData(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Getter for hours
     * @return int representing hours the timer was set for
     */
    public int getHours() {
        return hours;
    }

    /**
     * getter for minutes
     * @return int representing minutes the timer was set for
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * getter for seconds
     * @return int representing seconds the timer was set for
     */
    public int getSeconds() {
        return seconds;
    }
}
