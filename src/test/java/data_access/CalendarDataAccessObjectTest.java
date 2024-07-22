package data_access;

import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Course;
import entity.Task;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarDataAccessObjectTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "src/main/java/data_access/userCache.json";
    private Gson gson;

    @BeforeEach
    void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .setPrettyPrinting()
                .create();
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void GetCalendarRightAfterCreatingNewUser() {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
        User user = new User("user1", "password1", friends, courses);
        user.addTask(task);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("User exists: " + fileCacheUserDAO.UserExists("user1"));

           CalendarDataAccessObject obj = new CalendarDataAccessObject("user1");
           Calendar calendar = obj.getCalendar();
           assertTrue(calendar != null);
           assertTrue(calendar.getAllEvents().isEmpty());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void AddEventToUserAndRetrieveThem() {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
        User user = new User("user1", "password1", friends, courses);
        user.addTask(task);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("User exists: " + fileCacheUserDAO.UserExists("user1"));

            CalendarDataAccessObject obj = new CalendarDataAccessObject("user1");

            String name = "name";
            String description = "description";
            String priorityLevel = "High";
            LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 22, 14, 0);
            LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            CalendarEvent event = new CalendarEvent(name,description, priorityLevel, startDate, endDate);
            obj.addEvent(event);
            Calendar calendar = obj.getCalendar();
            assertTrue(calendar.getAllEvents().size() == 1);
            assertTrue(calendar.getAllEvents().contains(event));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }
    @Test
    void AddMultipleEventsToUserAndRetrieveThem() {
        User[] friends = {};
        Course[] courses = {new Course("CSC207", "Software Design")};
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CSC207");
        User user = new User("user1", "password1", friends, courses);
        user.addTask(task);
        try {
            fileCacheUserDAO.WriteToCache(user);
            System.out.println("User exists: " + fileCacheUserDAO.UserExists("user1"));

            CalendarDataAccessObject obj = new CalendarDataAccessObject("user1");

            String name = "name";
            String description = "description";
            String priorityLevel = "High";
            LocalDateTime startDate = LocalDateTime.of(2024, Month.JULY, 22, 14, 0);
            LocalDateTime endDate = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            CalendarEvent event = new CalendarEvent(name,description, priorityLevel, startDate, endDate);
            obj.addEvent(event);

            String nameTwo = "nameTwo";
            String descriptionTwo = "descriptionTwo";
            String priorityLevelTwo = "Normal";
            LocalDateTime startDateTwo = LocalDateTime.of(2024, Month.JULY, 22, 15, 0);
            LocalDateTime endDateTwo = LocalDateTime.of(2024, Month.JULY, 22, 16, 0);
            CalendarEvent eventTwo = new CalendarEvent(nameTwo,descriptionTwo, priorityLevelTwo, startDateTwo, endDateTwo);
            obj.addEvent(eventTwo);

            String nameThree = "nameThree";
            String descriptionThree = "descriptionThree";
            String priorityLevelThree = "Low";
            LocalDateTime startDateThree = LocalDateTime.of(2024, Month.JULY, 23, 15, 0);
            LocalDateTime endDateThree = LocalDateTime.of(2024, Month.JULY, 23, 16, 0);
            CalendarEvent eventThree = new CalendarEvent(nameThree,descriptionThree, priorityLevelThree, startDateThree, endDateThree);
            obj.addEvent(eventThree);

            Calendar calendar = obj.getCalendar();
            assertTrue(calendar.getAllEvents().size() == 3);
            assertTrue(calendar.getAllEvents().contains(event));
            assertTrue(calendar.getAllEvents().contains(eventTwo));
            assertTrue(calendar.getAllEvents().contains(eventThree));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }
}
