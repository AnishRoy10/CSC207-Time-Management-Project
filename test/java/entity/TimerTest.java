package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the timer entity methods
 */
class TimerTest {
    private Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer(1, 0, 0);
    }

    @Test
    void updateElapsed_time() {
        long expected = System.currentTimeMillis() - timer.getStart_time();
        timer.updateElapsed_time();
        long actual = timer.getElapsed_time();
        assertEquals(expected, actual);
    }

    @Test
    void checkTimer() {
        timer.updateElapsed_time();
        assertFalse(timer.checkTimer());
        timer = new Timer(0, 0, 0);
        timer.updateElapsed_time();
        assertTrue(timer.checkTimer());
    }

    @Test
    void timerLength() {
        long actual = timer.timerLength();
        assertEquals(3600000, actual);
    }

    @Test
    void pause() {
        long seconds = 100;
        long start = System.currentTimeMillis();
        timer.pause();
        while (System.currentTimeMillis() < start + seconds) {
            timer.updateElapsed_time();
        }
        timer.resume();
        long actual = timer.timerLength();
        assertEquals(3600100, actual);
    }
}