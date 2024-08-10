package use_case.CalendarUseCases.AddEventUseCase;

import data_access.CalendarDataAccessObject;
import data_access.SQLDatabaseHelper;
import data_access.UserDAO;
import entity.CalendarEvent;
import interface_adapter.AddEvent.AddEventPresenter;
import interface_adapter.AddEvent.AddEventViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AddEventUseCaseInteractorTest {
    private SQLDatabaseHelper dbHelper;
    private UserDAO userDAO;
    private CalendarDataAccessObject calendarDAO;
    private AddEventPresenter presenter;
    private AddEventViewModel viewModel;
    private AddEventUseCaseInteractor interactor;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() throws IOException {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userDAO = new UserDAO(dbHelper);
        calendarDAO = new CalendarDataAccessObject("testUser", dbHelper);
        viewModel = new AddEventViewModel();
        presenter = new AddEventPresenter(viewModel);
        interactor = new AddEventUseCaseInteractor(calendarDAO, presenter);

        // Create and save the user
        User user = new User("testUser", "password", new User[]{}, new entity.Course[]{});
        userDAO.WriteToCache(user);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM CalendarEvents");
            stmt.execute("DELETE FROM Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testAddEventSuccess() throws IOException, ClassNotFoundException {
        CalendarEvent event = new CalendarEvent("Test Event", "Description", "High",
                LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        AddEventInputData inputData = new AddEventInputData(event);

        interactor.execute(inputData);

        assertFalse(viewModel.getStartEndError());
        assertFalse(viewModel.getPriorityLevelError());
        assertFalse(viewModel.getStartAfterEndError());

        // Verify that the event was added to the database
        entity.Calendar calendar = calendarDAO.getCalendar();
        assertEquals(1, calendar.getAllEvents().size());
        assertEquals("Test Event", calendar.getAllEvents().get(0).getName());
    }

    @Test
    void testAddEventStartEndError() throws IOException, ClassNotFoundException {
        CalendarEvent event = new CalendarEvent("Test Event", "Description", "High",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1).plusMinutes(1)); // Start and end are not on the same day

        AddEventInputData inputData = new AddEventInputData(event);

        interactor.execute(inputData);

        assertTrue(viewModel.getStartEndError());
        assertFalse(viewModel.getPriorityLevelError());
        assertFalse(viewModel.getStartAfterEndError());

        // Verify that the event was not added to the database
        entity.Calendar calendar = calendarDAO.getCalendar();
        assertEquals(0, calendar.getAllEvents().size());
    }

    @Test
    void testAddEventPriorityLevelError() throws IOException, ClassNotFoundException {
        CalendarEvent event = new CalendarEvent("Test Event", "Description", "InvalidPriority",
                LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        AddEventInputData inputData = new AddEventInputData(event);

        interactor.execute(inputData);

        assertFalse(viewModel.getStartEndError());
        assertTrue(viewModel.getPriorityLevelError());
        assertFalse(viewModel.getStartAfterEndError());

        // Verify that the event was not added to the database
        entity.Calendar calendar = calendarDAO.getCalendar();
        assertEquals(0, calendar.getAllEvents().size());
    }

    @Test
    void testAddEventStartAfterEndError() throws IOException, ClassNotFoundException {
        CalendarEvent event = new CalendarEvent("Test Event", "Description", "High",
                LocalDateTime.now().plusHours(1), LocalDateTime.now()); // Start is after end

        AddEventInputData inputData = new AddEventInputData(event);

        interactor.execute(inputData);

        assertFalse(viewModel.getStartEndError());
        assertFalse(viewModel.getPriorityLevelError());
        assertTrue(viewModel.getStartAfterEndError());

        // Verify that the event was not added to the database
        entity.Calendar calendar = calendarDAO.getCalendar();
        assertEquals(0, calendar.getAllEvents().size());
    }

    @Test
    void testAddEventMultipleErrors() throws IOException, ClassNotFoundException {
        CalendarEvent event = new CalendarEvent("Test Event", "Description", "InvalidPriority",
                LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(1).plusMinutes(1)); // Multiple errors

        AddEventInputData inputData = new AddEventInputData(event);

        interactor.execute(inputData);

        assertTrue(viewModel.getStartEndError());
        assertTrue(viewModel.getPriorityLevelError());

        // Verify that the event was not added to the database
        entity.Calendar calendar = calendarDAO.getCalendar();
        assertEquals(0, calendar.getAllEvents().size());
    }
}
