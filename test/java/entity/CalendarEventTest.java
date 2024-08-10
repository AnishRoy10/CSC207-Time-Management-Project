package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarEventTest {
    private CalendarEvent event1;
    private CalendarEvent event2;
    private CalendarEvent event3;

    @BeforeEach
    void setUp() {
        event1 = new CalendarEvent("Event 1", "Description 1", "High",
                LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0),
                LocalDateTime.of(2024, Month.AUGUST, 8, 11, 0));
        event2 = new CalendarEvent("Event 2", null, "Medium",
                LocalDateTime.of(2024, Month.AUGUST, 9, 12, 0),
                LocalDateTime.of(2024, Month.AUGUST, 9, 13, 0));
        event3 = new CalendarEvent("Event 3", "Description 3", null,
                LocalDateTime.of(2024, Month.AUGUST, 10, 14, 0),
                null);
    }

    @Test
    void testGetters() {
        assertEquals("Event 1", event1.getName());
        assertEquals("Description 1", event1.getDescription());
        assertEquals("High", event1.getPriorityLevel());
        assertEquals(LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0), event1.getStartDate());
        assertEquals(LocalDateTime.of(2024, Month.AUGUST, 8, 11, 0), event1.getEndDate());
        assertEquals("Finished", event1.getStatus());
        assertTrue(event1.getHasEndDate());

        assertEquals("Event 2", event2.getName());
        assertEquals("No description specified for this Event", event2.getDescription());
        assertEquals("Medium", event2.getPriorityLevel());
        assertEquals(LocalDateTime.of(2024, Month.AUGUST, 9, 12, 0), event2.getStartDate());
        assertEquals(LocalDateTime.of(2024, Month.AUGUST, 9, 13, 0), event2.getEndDate());
        assertEquals("Upcoming", event2.getStatus());
        assertTrue(event2.getHasEndDate());

        assertEquals("Event 3", event3.getName());
        assertEquals("Description 3", event3.getDescription());
        assertEquals("Normal", event3.getPriorityLevel());
        assertEquals(LocalDateTime.of(2024, Month.AUGUST, 10, 14, 0), event3.getStartDate());
        assertNull(event3.getEndDate());
        assertEquals("Upcoming", event3.getStatus());
        assertFalse(event3.getHasEndDate());
    }

    @Test
    void testSetters() {
        event1.setName("New Event 1");
        event1.setDescription("New Description 1");
        event1.setPriorityLevel("Low");
        event1.setStartDate(LocalDateTime.of(2024, Month.AUGUST, 8, 9, 0));
        event1.setEndDate(LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0));
        event1.setStatus("In Progress");

        assertEquals("New Event 1", event1.getName());
        assertEquals("New Description 1", event1.getDescription());
        assertEquals("Low", event1.getPriorityLevel());
        assertEquals(LocalDateTime.of(2024, Month.AUGUST, 8, 9, 0), event1.getStartDate());
        assertEquals(LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0), event1.getEndDate());
        assertEquals("In Progress", event1.getStatus());
    }

    @Test
    void testUpdateStatus() {
        event1.updateStatus();
        assertEquals("Finished", event1.getStatus());

        CalendarEvent ongoingEvent = new CalendarEvent("Ongoing Event", "Ongoing Description", "High",
                LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));
        ongoingEvent.updateStatus();
        assertEquals("In Progress", ongoingEvent.getStatus());

        CalendarEvent finishedEvent = new CalendarEvent("Finished Event", "Finished Description", "High",
                LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1));
        finishedEvent.updateStatus();
        assertEquals("Finished", finishedEvent.getStatus());
    }

    @Test
    void testEventsBetweenDates() {
        List<CalendarEvent> eventList = new ArrayList<>();
        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);

        List<CalendarEvent> result = CalendarEvent.eventsBetweenDates(
                LocalDateTime.of(2024, Month.AUGUST, 8, 0, 0),
                LocalDateTime.of(2024, Month.AUGUST, 9, 23, 59),
                eventList);

        assertEquals(2, result.size());
        assertTrue(result.contains(event1));
        assertTrue(result.contains(event2));
        assertFalse(result.contains(event3));
    }

    @Test
    void testStartEndOnSameDay() {
        assertTrue(event1.startEndOnSameDay());
        assertTrue(event2.startEndOnSameDay());
        assertFalse(event3.getHasEndDate());

        CalendarEvent differentDayEvent = new CalendarEvent("Different Day Event", "Description", "High",
                LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0),
                LocalDateTime.of(2024, Month.AUGUST, 9, 10, 0));
        assertFalse(differentDayEvent.startEndOnSameDay());
    }

    @Test
    void testPriorityLevelIsValid() {
        assertTrue(event1.priorityLevelIsValid());
        assertFalse(event2.priorityLevelIsValid());

        CalendarEvent invalidPriorityEvent = new CalendarEvent("Invalid Priority Event", "Description", "Very High",
                LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0),
                LocalDateTime.of(2024, Month.AUGUST, 8, 11, 0));
        assertFalse(invalidPriorityEvent.priorityLevelIsValid());
    }

    @Test
    void testStartBeforeEnd() {
        assertTrue(event1.startBeforeEnd());
        assertTrue(event2.startBeforeEnd());
        assertFalse(event3.getHasEndDate());

        CalendarEvent invalidEvent = new CalendarEvent("Invalid Event", "Description", "High",
                LocalDateTime.of(2024, Month.AUGUST, 8, 12, 0),
                LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0));
        assertFalse(invalidEvent.startBeforeEnd());
    }

    @Test
    void testToString() {
        String eventString = event1.toString();
        assertTrue(eventString.contains("Event 1"));
        assertTrue(eventString.contains("Description 1"));
        assertTrue(eventString.contains("High"));
        assertTrue(eventString.contains(event1.getStartDate().toString()));
        assertTrue(eventString.contains(event1.getEndDate().toString()));
    }

    @Test
    void testEqualsAndHashCode() {
        CalendarEvent anotherEvent = new CalendarEvent("Event 1", "Description 1", "High",
                LocalDateTime.of(2024, Month.AUGUST, 8, 10, 0),
                LocalDateTime.of(2024, Month.AUGUST, 8, 11, 0));
        assertTrue(event1.equals(anotherEvent));
        assertEquals(event1.hashCode(), anotherEvent.hashCode());

        anotherEvent.setName("Different Event");
        assertFalse(event1.equals(anotherEvent));
        assertNotEquals(event1.hashCode(), anotherEvent.hashCode());
    }
}
