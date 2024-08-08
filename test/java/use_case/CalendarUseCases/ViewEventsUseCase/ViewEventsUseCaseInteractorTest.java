package use_case.CalendarUseCases.ViewEventsUseCase;

import data_access.CalendarDataAccessObject;
import data_access.SQLDatabaseHelper;
import data_access.UserDAO;
import entity.CalendarEvent;
import entity.User;
import interface_adapter.ViewEvents.ViewEventsPresenter;
import interface_adapter.ViewEvents.ViewEventsViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewEventsUseCaseInteractorTest {
    private SQLDatabaseHelper dbHelper;
    private UserDAO userDAO;
    private CalendarDataAccessObject calendarDAO;
    private ViewEventsPresenter presenter;
    private ViewEventsViewModel viewModel;
    private ViewEventsUseCaseInteractor interactor;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() throws IOException {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userDAO = new UserDAO(dbHelper);
        calendarDAO = new CalendarDataAccessObject("testUser", dbHelper);
        viewModel = new ViewEventsViewModel();
        presenter = new ViewEventsPresenter(viewModel);
        interactor = new ViewEventsUseCaseInteractor(calendarDAO, presenter);

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
    void testViewEventsSuccess() throws IOException, ClassNotFoundException {
        // Add test events to the calendar
        CalendarEvent event1 = new CalendarEvent("Event 1", "Description 1", "High",
                LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        CalendarEvent event2 = new CalendarEvent("Event 2", "Description 2", "Medium",
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1));
        calendarDAO.addEvent(event1);
        calendarDAO.addEvent(event2);

        // Create input data
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        ViewEventsInputData inputData = new ViewEventsInputData(start, end);

        // Execute the use case
        interactor.execute(inputData);

        // Verify the output data
        List<CalendarEvent> events = viewModel.getEventListToBeShown();
        assertEquals(2, events.size());
    }

    @Test
    void testViewEventsNoEvents() throws IOException, ClassNotFoundException {
        // Create input data with a range that has no events
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        ViewEventsInputData inputData = new ViewEventsInputData(start, end);

        // Execute the use case
        interactor.execute(inputData);

        // Verify the output data
        List<CalendarEvent> events = viewModel.getEventListToBeShown();
        assertTrue(events.isEmpty());
    }

    @Test
    void testViewEventsOutsideRange() throws IOException, ClassNotFoundException {
        // Add a test event outside the range
        CalendarEvent event = new CalendarEvent("Outside Event", "Description", "Low",
                LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1));
        calendarDAO.addEvent(event);

        // Create input data
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        ViewEventsInputData inputData = new ViewEventsInputData(start, end);

        // Execute the use case
        interactor.execute(inputData);

        // Verify the output data
        List<CalendarEvent> events = viewModel.getEventListToBeShown();
        assertTrue(events.isEmpty());
    }
}
