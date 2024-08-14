package entity;

import java.io.Serializable;

/**
 * Represents a timer.
 * Each timer has a start time, end time and elapsed time.
 */
public class Timer implements Serializable {
    private static final long serialVersionUID = 9L;

    private final long start_time;
    private long end_time;
    private long elapsed_time;
    private long pause_time;

    /**
     * Creates a timer with a specified length.
     * @param hours The amount of hours the timer lasts
     * @param minutes The amount of minutes the timer lasts
     * @param seconds The amount of seconds the timer lasts
     */
    public Timer(int hours, int minutes, int seconds) {
        long timer_length = hours * 3600000L + minutes * 60000L + seconds * 1000L;
        start_time = System.currentTimeMillis();
        end_time = start_time + timer_length;
        elapsed_time = 0;
    }

    /**
     * Creates a timer with specified start, end, elapsed, and pause times.
     * @param start_time The start time of the timer
     * @param end_time The end time of the timer
     * @param elapsed_time The elapsed time of the timer
     * @param pause_time The pause time of the timer
     */
    public Timer(long start_time, long end_time, long elapsed_time, long pause_time) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.elapsed_time = elapsed_time;
        this.pause_time = pause_time;
    }

    /**
     * Gets the elapsed time since timer has started.
     * @return A long type representing elapsed time.
     */
    public long getElapsed_time() {
        return elapsed_time;
    }

    /**
     * Gets the start time since timer has started.
     * @return A long type representing start time
     */
    public long getStart_time() {
        return start_time;
    }

    /**
     * Gets the end time of the timer.
     * @return A long type representing end time
     */
    public long getEnd_time() {
        return end_time;
    }

    /**
     * Gets the pause time of the timer.
     * @return A long type representing pause time
     */
    public long getPause_time() {
        return pause_time;
    }

    /**
     * Updates the elapsed time since timer has started.
     * Elapsed time cannot be greater than the timer length.
     */
    public void updateElapsed_time() {
        elapsed_time = System.currentTimeMillis() - start_time;
        if (elapsed_time + start_time > end_time) {
            elapsed_time = end_time - start_time;
        }
    }

    /**
     * Updates the elapsed time with a specific value.
     * @param elapsed_time The elapsed time to set
     */
    public void updateElapsed_time(long elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    /**
     * Updates the pause time with a specific value.
     * @param pause_time The pause time to set
     */
    public void pause(long pause_time) {
        this.pause_time = pause_time;
    }

    /**
     * Checks whether the specified timer length has passed.
     * @return A Boolean representing whether the specified timer length has passed.
     */
    public boolean checkTimer() {
        return elapsed_time + start_time >= end_time;
    }

    /**
     * Returns the total length of the timer
     * @return total length of timer
     */
    public long timerLength() {
        return end_time - start_time;
    }

    /**
     * Records when the timer was paused.
     */
    public void pause() {
        pause_time = System.currentTimeMillis();
    }

    /**
     * The amount of time paused is added to the end time.
     */
    public void resume() {
        end_time += System.currentTimeMillis() - pause_time;
    }
}
