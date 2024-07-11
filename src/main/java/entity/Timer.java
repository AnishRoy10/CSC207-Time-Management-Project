package entity;
/**
Represents a timer.
 Each timer has a start time, end time and elasped time.
 */
public class Timer {
    private final long start_time;
    private final long end_time;
    private long elapsed_time;

    /**
     * Creates a timer with a specified length.
     * @param timer_length The timer's length in milliseconds
     */
    public Timer(long timer_length) {
        start_time = System.currentTimeMillis();
        end_time = start_time + timer_length;
        elapsed_time = 0;
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
     * Updates the elapsed time since timer has started.
     */
    public void updateElapsed_time() {
        elapsed_time = System.currentTimeMillis() - start_time;
    }

    /**
     * Checks whether the specified timer length has passed.
     * @return A Boolean representing whether the specified timer length has passed.
     */
    public boolean checkTimer() {
        return elapsed_time + start_time > end_time;
    }
}
