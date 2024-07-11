package use_case.timer;

public class SetTimerInputData {
    final private int hours;
    final private int minutes;
    final private int seconds;

    public SetTimerInputData(int hours, int minutes, int seconds) {
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