package use_case.TimerUseCases.SetTimerUseCase;

/**
 * Class representing the input data for the set timer use case.
 */
public class SetTimerInputData {
    final private int hours;
    final private int minutes;
    final private int seconds;

    /**
     * Constructor for SetTimerInputData.
     * @param hours int representing amount of hours the timer lasts for
     * @param minutes int representing amount of minutes the timer lasts for
     * @param seconds int representing amount of seconds the timer lasts for
     */
    public SetTimerInputData(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Getter method for hours.
     * @return int representing amount of hours the timer lasts for
     */
    public int getHours() {
        return hours;
    }

    /**
     * Getter method for minutes.
     * @return int representing amount of minutes the timer lasts for
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Getter method for seconds.
     * @return int representing amount of seconds the timer lasts for
     */
    public int getSeconds() {
        return seconds;
    }

}
