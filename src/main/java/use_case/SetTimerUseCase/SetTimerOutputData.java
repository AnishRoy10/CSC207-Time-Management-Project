package use_case.SetTimerUseCase;

public class SetTimerOutputData {
    private final int hours;
    private final int minutes;
    private final int seconds;

    public SetTimerOutputData(int hours, int minutes, int seconds) {
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
