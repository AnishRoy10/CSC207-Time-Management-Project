package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
public class CalendarTest {

    // Creating three events
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
    LocalDateTime startDateThree = LocalDateTime.of(2024, Month.JULY, 31, 23, 45);
    LocalDateTime endDateThree = LocalDateTime.of(2024, Month.JULY, 31, 23, 50);
    CalendarEvent eventThree = new CalendarEvent(nameThree, descriptionThree, priorityLevelThree, startDateThree, endDateThree);

    @Test
    void AddEventGetAllEventsTest() {
        Calendar calendar = new Calendar();
        assertTrue(calendar.getAllEvents().isEmpty());

        calendar.addEvent(event);
        assertTrue(calendar.getAllEvents().size() == 1);
        assertTrue(calendar.getAllEvents().contains(event));

        calendar.addEvent(eventTwo);
        assertTrue(calendar.getAllEvents().size() == 2);
        assertTrue(calendar.getAllEvents().contains(event));
        assertTrue(calendar.getAllEvents().contains(eventTwo));

        calendar.addEvent(eventThree);
        assertTrue(calendar.getAllEvents().size() == 3);
        assertTrue(calendar.getAllEvents().contains(event));
        assertTrue(calendar.getAllEvents().contains(eventTwo));
        assertTrue(calendar.getAllEvents().contains(eventThree));
    }

    @Test
    void removeEventsTest() {
        Calendar calendar = new Calendar();
        calendar.addEvent(event);
        calendar.addEvent(eventTwo);
        calendar.addEvent(eventThree);

        calendar.removeEvent(eventThree);
        assertTrue(calendar.getAllEvents().size() == 2);
        assertTrue(calendar.getAllEvents().contains(event));
        assertTrue(calendar.getAllEvents().contains(eventTwo));

        calendar.removeEvent(eventTwo);
        assertTrue(calendar.getAllEvents().size() == 1);
        assertTrue(calendar.getAllEvents().contains(event));

        calendar.removeEvent(event);
        assertTrue(calendar.getAllEvents().isEmpty());
    }

    @Test
    void eventsBetweenDatesTest() {
        Calendar calendar = new Calendar();
        calendar.addEvent(event);
        calendar.addEvent(eventTwo);
        calendar.addEvent(eventThree);

        LocalDateTime testStartDate = LocalDateTime.of(2024, Month.JULY, 30, 15, 59);
        LocalDateTime testEndDate = LocalDateTime.of(2024, Month.JULY, 30, 16, 31);
        assertTrue(calendar.eventsBetweenDates(testStartDate, testEndDate).size() == 1);
        assertTrue(calendar.eventsBetweenDates(testStartDate, testEndDate).contains(eventTwo));

        LocalDateTime testStartDateTwo = LocalDateTime.of(2024, Month.JULY, 29, 0, 59);
        LocalDateTime testEndDateTwo = LocalDateTime.of(2024, Month.JULY, 31, 23, 59);
        assertTrue(calendar.eventsBetweenDates(testStartDateTwo, testEndDateTwo).size() == 3);
        assertTrue(calendar.eventsBetweenDates(testStartDateTwo, testEndDateTwo).contains(eventTwo));
        assertTrue(calendar.eventsBetweenDates(testStartDateTwo, testEndDateTwo).contains(event));
        assertTrue(calendar.eventsBetweenDates(testStartDateTwo, testEndDateTwo).contains(eventThree));

        LocalDateTime testStartDateThree = LocalDateTime.of(2024, Month.JUNE, 29, 0, 59);
        LocalDateTime testEndDateThree = LocalDateTime.of(2024, Month.JUNE, 30, 23, 59);
        assertTrue(calendar.eventsBetweenDates(testStartDateThree, testEndDateThree).isEmpty());
    }
}
