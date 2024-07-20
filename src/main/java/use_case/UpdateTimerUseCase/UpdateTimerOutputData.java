package use_case.UpdateTimerUseCase;
/**
 * Output data for the update timer use case.
 */
public class UpdateTimerOutputData {
    private final int hours;
    private final int minutes;
    private final int seconds;

    public UpdateTimerOutputData(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

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
