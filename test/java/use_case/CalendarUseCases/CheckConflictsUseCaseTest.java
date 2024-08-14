package use_case.CalendarUseCases;

import entity.CalendarEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CheckConflictsUseCaseTest {

    private CalendarEvent event1;
    private CalendarEvent event2;
    private CalendarEvent event3;
    private CalendarEvent event4;

    @BeforeEach
    public void setUp() {
        // Set up some sample CalendarEvents
        event1 = new CalendarEvent("Event 1", "Description 1", "High", LocalDateTime.of(2024, 8, 15, 10, 0), null);
        event2 = new CalendarEvent("Event 2", "Description 2", "Low", LocalDateTime.of(2024, 8, 15, 11, 0), null);
        event3 = new CalendarEvent("Event 3", "Description 3", "Medium", LocalDateTime.of(2024, 8, 15, 12, 0), null);
        event4 = new CalendarEvent("Event 4", "Description 4", "High", LocalDateTime.of(2024, 8, 15, 10, 30), LocalDateTime.of(2024, 8, 15, 11, 30));
    }

    @Test
    public void testComesFirst() {
        // Test if comesFirst correctly orders two events
        ArrayList<CalendarEvent> orderedEvents = CheckConflictsUseCase.comesFirst(event1, event2);
        assertEquals(event1, orderedEvents.get(0));
        assertEquals(event2, orderedEvents.get(1));
    }

    @Test
    public void testComesFirstSameStartDate() {
        // Test if comesFirst returns events in the order they were passed if start dates are the same
        CalendarEvent eventSameStart1 = new CalendarEvent("Event 5", "Description 5", "Medium", event1.getStartDate(), null);
        CalendarEvent eventSameStart2 = new CalendarEvent("Event 6", "Description 6", "Low", event1.getStartDate(), null);

        ArrayList<CalendarEvent> orderedEvents = CheckConflictsUseCase.comesFirst(eventSameStart1, eventSameStart2);
        assertEquals(eventSameStart1, orderedEvents.get(0));
        assertEquals(eventSameStart2, orderedEvents.get(1));
    }

    @Test
    public void testConflictCheckerNoConflict() {
        // Test conflictChecker with events that do not conflict
        assertFalse(CheckConflictsUseCase.conflictChecker(event1, event2));
    }

    @Test
    public void testShowConflictsNoConflict() {
        // Test showConflicts with no conflicting events
        ArrayList<CalendarEvent> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        ArrayList<ArrayList<CalendarEvent>> conflicts = CheckConflictsUseCase.showConflicts(events);

        // Verify that no conflicts were detected
        assertTrue(conflicts.isEmpty());
    }

}
