package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarEventTest {
    @Test
    void TestEventsBetweenDatesMethod(){
        String name = "Name";
        String description = "This is a description for this event";
        String priorityLevel = "Low";
        LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 30, 22, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 30, 23, 30);
        CalendarEvent event = new CalendarEvent(name, description, priorityLevel, startDate, endDate);

        String nameTwo = "Name Two";
        String descriptionTwo = "This is a description for this event number Two";
        String priorityLevelTwo = "High";
        LocalDateTime startDateTwo = LocalDateTime.of(2024, Month.JULY, 30, 16, 0);
        LocalDateTime endDateTwo = LocalDateTime.of(2024, Month.JULY, 30, 16, 30);
        CalendarEvent eventTwo = new CalendarEvent(nameTwo, descriptionTwo, priorityLevelTwo, startDateTwo, endDateTwo);

        String nameThree = "Name three";
        String descriptionThree = "This is a description for this event number three";
        String priorityLevelThree = "Normal";
        LocalDateTime startDateThree = LocalDateTime.of(2024, Month.JULY, 30, 23, 45);
        LocalDateTime endDateThree = LocalDateTime.of(2024, Month.JULY, 30, 23, 50);
        CalendarEvent eventThree = new CalendarEvent(nameThree, descriptionThree, priorityLevelThree, startDateThree, endDateThree);

        List<CalendarEvent> eventList = new ArrayList<>();
        eventList.add(event);
        eventList.add(eventTwo);
        eventList.add(eventThree);

        LocalDateTime testStart = LocalDateTime.of(2024, Month.JULY, 30, 15, 30);
        LocalDateTime testEnd = LocalDateTime.of(2024, Month.JULY, 30, 16, 50);
        assertTrue(CalendarEvent.eventsBetweenDates(testStart, testEnd, eventList).contains(eventTwo));
        assertTrue(CalendarEvent.eventsBetweenDates(testStart, testEnd, eventList).size() == 1);

        LocalDateTime testStartTwo = LocalDateTime.of(2024, Month.JULY, 29, 15, 59);
        LocalDateTime testEndTwo = LocalDateTime.of(2024, Month.JULY, 31, 16, 45);
        assertTrue(CalendarEvent.eventsBetweenDates(testStartTwo, testEndTwo, eventList).size() == 3);

        LocalDateTime testStartThree = LocalDateTime.of(2024, Month.JULY, 29, 15, 59);
        LocalDateTime testEndThree = LocalDateTime.of(2024, Month.JULY, 29, 16, 45);
        assertTrue(CalendarEvent.eventsBetweenDates(testStartThree, testEndThree, eventList).isEmpty());






    }
}
