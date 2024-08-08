package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {
    private Calendar calendar;
    private CalendarEvent event1;
    private CalendarEvent event2;
    private CalendarEvent event3;

    @BeforeEach
    void setUp() {
        calendar = new Calendar();
        event1 = new CalendarEvent("Event 1", "Description 1", "High",
                LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0),
                LocalDateTime.of(2024, Month.AUGUST, 8, 11, 0));
        event2 = new CalendarEvent("Event 2", "Description 2", "Medium",
                LocalDateTime.of(2024, Month.AUGUST, 9, 12, 0),
                LocalDateTime.of(2024, Month.AUGUST, 9, 13, 0));
        event3 = new CalendarEvent("Event 3", "Description 3", "Low",
                LocalDateTime.of(2024, Month.AUGUST, 10, 14, 0),
                LocalDateTime.of(2024, Month.AUGUST, 10, 15, 0));
    }

    @Test
    void testAddEvent() {
        calendar.addEvent(event1);
        assertTrue(calendar.hasEventsOnDate(event1.getStartDate()));
        assertEquals(1, calendar.getEventsForDate(event1.getStartDate()).size());
        assertEquals(event1, calendar.getEventsForDate(event1.getStartDate()).get(0));
    }

    @Test
    void testRemoveEvent() {
        calendar.addEvent(event1);
        calendar.removeEvent(event1);
        assertFalse(calendar.hasEventsOnDate(event1.getStartDate()));
        assertTrue(calendar.getEventsForDate(event1.getStartDate()).isEmpty());
    }

    @Test
    void testGetEventsForDate() {
        calendar.addEvent(event1);
        calendar.addEvent(event2);
        List<CalendarEvent> events = calendar.getEventsForDate(event1.getStartDate());
        assertEquals(1, events.size());
        assertEquals(event1, events.get(0));
    }

    @Test
    void testGetAllEvents() {
        calendar.addEvent(event1);
        calendar.addEvent(event2);
        calendar.addEvent(event3);
        List<CalendarEvent> allEvents = calendar.getAllEvents();
        assertEquals(3, allEvents.size());
        assertTrue(allEvents.contains(event1));
        assertTrue(allEvents.contains(event2));
        assertTrue(allEvents.contains(event3));
    }

    @Test
    void testHasEventsOnDate() {
        assertFalse(calendar.hasEventsOnDate(event1.getStartDate()));
        calendar.addEvent(event1);
        assertTrue(calendar.hasEventsOnDate(event1.getStartDate()));
    }

    @Test
    void testEventsBetweenDates() {
        calendar.addEvent(event1);
        calendar.addEvent(event2);
        calendar.addEvent(event3);
        List<CalendarEvent> events = calendar.eventsBetweenDates(
                LocalDateTime.of(2024, Month.AUGUST, 8, 0, 0),
                LocalDateTime.of(2024, Month.AUGUST, 9, 23, 59));
        assertEquals(2, events.size());
        assertTrue(events.contains(event1));
        assertTrue(events.contains(event2));
        assertFalse(events.contains(event3));
    }

    @Test
    void testHasEventsBetweenDates() {
        calendar.addEvent(event1);
        calendar.addEvent(event2);
        assertTrue(calendar.hasEventsBetweenDates(
                LocalDateTime.of(2024, Month.AUGUST, 8, 0, 0),
                LocalDateTime.of(2024, Month.AUGUST, 9, 23, 59)));
        assertFalse(calendar.hasEventsBetweenDates(
                LocalDateTime.of(2024, Month.AUGUST, 11, 0, 0),
                LocalDateTime.of(2024, Month.AUGUST, 12, 23, 59)));
    }

    @Test
    void testToString() {
        calendar.addEvent(event1);
        calendar.addEvent(event2);
        String calendarString = calendar.toString();
        assertTrue(calendarString.contains("Date: " + event1.getStartDate().toString()));
        assertTrue(calendarString.contains(event1.toString()));
        assertTrue(calendarString.contains("Date: " + event2.getStartDate().toString()));
        assertTrue(calendarString.contains(event2.toString()));
    }

    @Test
    void testEquals() {
        Calendar anotherCalendar = new Calendar();
        assertTrue(calendar.equals(anotherCalendar));
        calendar.addEvent(event1);
        assertFalse(calendar.equals(anotherCalendar));
        anotherCalendar.addEvent(event1);
        assertTrue(calendar.equals(anotherCalendar));
    }

    @Test
    void testHashCode() {
        Calendar anotherCalendar = new Calendar();
        assertEquals(calendar.hashCode(), anotherCalendar.hashCode());
        calendar.addEvent(event1);
        anotherCalendar.addEvent(event1);
        assertEquals(calendar.hashCode(), anotherCalendar.hashCode());
    }
}
