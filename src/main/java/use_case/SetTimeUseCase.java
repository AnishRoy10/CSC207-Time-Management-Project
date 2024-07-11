package use_case;

import entity.Timer;
import java.util.concurrent.TimeUnit;

/*
The SetTimeUseCase class handles the use case of setting the time for
the timer and creating a Timer class to represent the Timer.
 */
public class SetTimeUseCase {

    //TODO need to store the Timer class somewhere
    public SetTimeUseCase() {
    }

    // Takes in the hours/minutes/seconds length of the timer, converts to milliseconds
    // and sets the timer class
    public Timer SetTime(int hours, int minutes, int seconds) {
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        if (seconds < 0) {
            throw new IllegalArgumentException("Seconds cannot be negative");
        }

        long timer_length = TimeUnit.HOURS.toMillis(hours) +
                TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
        return new Timer(timer_length);
    }
}