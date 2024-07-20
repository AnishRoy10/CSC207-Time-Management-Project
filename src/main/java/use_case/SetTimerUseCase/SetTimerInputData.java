package use_case.SetTimerUseCase;

/**
 * Input data for the Set Timer Use Case.
 */
public class SetTimerInputData {
    final private int hours;
    final private int minutes;
    final private int seconds;

    public SetTimerInputData(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    //Getters

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

}
