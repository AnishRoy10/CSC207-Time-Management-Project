package entity;

public class Timer {
    private long start_time;
    private long end_time;
    private long elapsed_time;

    // timer length is in seconds
    public Timer(long timer_length) {
        start_time = System.currentTimeMillis();
        end_time = start_time + timer_length;
        elapsed_time = 0;
    }

    public long getElapsed_time() {
        return elapsed_time;
    }

    public void updateElapsed_time() {
        elapsed_time = System.currentTimeMillis() - start_time;
    }

    public boolean checkTimer() {
        return elapsed_time + start_time > end_time;
    }
}
