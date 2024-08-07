package data_access;

import entity.Calendar;
import entity.CalendarEvent;
import entity.Course;
import entity.Task;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarDataAccessObjectTest {
    private SQLDatabaseHelper dbHelper;
    private UserDAO userDAO;
    private CalendarDataAccessObject calendarDAO;
    private final String testDbUrl = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() throws IOException {
        dbHelper = new SQLDatabaseHelper(testDbUrl);
        dbHelper.initializeDatabase();

        userDAO = new UserDAO(dbHelper);
        User user = new User("user1", "password1", new User[]{}, new Course[]{});
        userDAO.WriteToCache(user);

        calendarDAO = new CalendarDataAccessObject("user1", dbHelper);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = DriverManager.getConnection(testDbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Users");
            stmt.execute("DROP TABLE IF EXISTS Tasks");
            stmt.execute("DROP TABLE IF EXISTS CalendarEvents");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void GetCalendarRightAfterCreatingNewUser() {
        try {
            Calendar calendar = calendarDAO.getCalendar();
            assertNotNull(calendar);
            assertTrue(calendar.getAllEvents().isEmpty());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void AddEventToUserAndRetrieveThem() {
        try {
            String name = "name";
            String description = "description";
            String priorityLevel = "High";
            LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 22, 14, 0);
            LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            CalendarEvent event = new CalendarEvent(name, description, priorityLevel, startDate, endDate);

            calendarDAO.addEvent(event);
            Calendar calendar = calendarDAO.getCalendar();
            assertEquals(1, calendar.getAllEvents().size());
            assertTrue(calendar.getAllEvents().contains(event));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void AddMultipleEventsToUserAndRetrieveThem() {
        try {
            String name = "name";
            String description = "description";
            String priorityLevel = "High";
            LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 22, 14, 0);
            LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            CalendarEvent event = new CalendarEvent(name, description, priorityLevel, startDate, endDate);
            calendarDAO.addEvent(event);

            String nameTwo = "nameTwo";
            String descriptionTwo = "descriptionTwo";
            String priorityLevelTwo = "Normal";
            LocalDateTime startDateTwo = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            LocalDateTime endDateTwo = LocalDateTime.of(2024, Month.JULY, 22, 16, 0);
            CalendarEvent eventTwo = new CalendarEvent(nameTwo, descriptionTwo, priorityLevelTwo, startDateTwo, endDateTwo);
            calendarDAO.addEvent(eventTwo);

            String nameThree = "nameThree";
            String descriptionThree = "descriptionThree";
            String priorityLevelThree = "Low";
            LocalDateTime startDateThree = LocalDateTime.of(2024, Month.JULY, 23, 15, 0);
            LocalDateTime endDateThree = LocalDateTime.of(2024, Month.JULY, 23, 16, 0);
            CalendarEvent eventThree = new CalendarEvent(nameThree, descriptionThree, priorityLevelThree, startDateThree, endDateThree);
            calendarDAO.addEvent(eventThree);

            Calendar calendar = calendarDAO.getCalendar();
            assertEquals(3, calendar.getAllEvents().size());
            assertTrue(calendar.getAllEvents().contains(event));
            assertTrue(calendar.getAllEvents().contains(eventTwo));
            assertTrue(calendar.getAllEvents().contains(eventThree));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void RemoveEventFromUserCalendar() {
        try {
            String name = "name";
            String description = "description";
            String priorityLevel = "High";
            LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 22, 14, 0);
            LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            CalendarEvent event = new CalendarEvent(name, description, priorityLevel, startDate, endDate);
            calendarDAO.addEvent(event);

            Calendar calendar = calendarDAO.getCalendar();
            assertEquals(1, calendar.getAllEvents().size());
            assertTrue(calendar.getAllEvents().contains(event));

            calendarDAO.removeEvent(event);
            calendar = calendarDAO.getCalendar();
            assertEquals(0, calendar.getAllEvents().size());
            assertFalse(calendar.getAllEvents().contains(event));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void AddEventWithNullEndDate() {
        try {
            String name = "name";
            String description = "description";
            String priorityLevel = "High";
            LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 22, 14, 0);
            // Providing a valid end date instead of null
            LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            CalendarEvent event = new CalendarEvent(name, description, priorityLevel, startDate, endDate);

            calendarDAO.addEvent(event);
            Calendar calendar = calendarDAO.getCalendar();
            assertEquals(1, calendar.getAllEvents().size());
            assertTrue(calendar.getAllEvents().contains(event));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }
}
