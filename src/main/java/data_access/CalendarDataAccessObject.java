package data_access;

import entity.Calendar;
import entity.CalendarEvent;
import entity.User;
import use_case.AddEventUseCase.AddEventDataAccessInterface;
import use_case.ViewEventsUseCase.ViewEventsDataAccessInterface;
import use_case.RemoveEventUseCase.RemoveEventDataAccessInterface;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * The CalendarDataAccessObject class is responsible for managing the caching of calendar use case related
 * classes to update the calendar of a user and retrieve information in the calendar of the user who is
 * logged in.
 */
public class CalendarDataAccessObject
        implements ViewEventsDataAccessInterface, AddEventDataAccessInterface, RemoveEventDataAccessInterface {

    private final UserDAO userDAO;
    private final String username;
    private final SQLDatabaseHelper dbHelper;

    public CalendarDataAccessObject(String username, SQLDatabaseHelper dbHelper) throws IOException {
        this.username = username;
        this.dbHelper = dbHelper;
        this.userDAO = new UserDAO(dbHelper);
    }

    @Override
    public Calendar getCalendar() throws IOException, ClassNotFoundException {
        Calendar calendar = new Calendar();
        String sql = "SELECT * FROM CalendarEvents WHERE username = ?";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CalendarEvent event = new CalendarEvent(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("priorityLevel"),
                        LocalDateTime.parse(rs.getString("startDate")),
                        rs.getString("endDate") != null ? LocalDateTime.parse(rs.getString("endDate")) : null
                );
                event.setStatus(rs.getString("status"));
                calendar.addEvent(event);
            }

        } catch (SQLException e) {
            throw new IOException("Failed to retrieve calendar", e);
        }

        return calendar;
    }

    @Override
    public void addEvent(CalendarEvent event) throws IOException, ClassNotFoundException {
        String sql = "INSERT INTO CalendarEvents(username, name, description, status, priorityLevel, startDate, endDate) VALUES(?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT(username, name, startDate) DO UPDATE SET description=excluded.description, status=excluded.status, priorityLevel=excluded.priorityLevel, endDate=excluded.endDate";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, event.getName());
            pstmt.setString(3, event.getDescription());
            pstmt.setString(4, event.getStatus());
            pstmt.setString(5, event.getPriorityLevel());
            pstmt.setString(6, event.getStartDate().toString());
            pstmt.setString(7, event.getEndDate() != null ? event.getEndDate().toString() : null);
            pstmt.executeUpdate();

            // Update the user's calendar in the UserDAO
            User user = userDAO.findByUsername(username);
            user.addEvent(event);
            userDAO.WriteToCache(user);

        } catch (SQLException e) {
            throw new IOException("Failed to add event", e);
        }
    }

    @Override
    public void removeEvent(CalendarEvent event) throws IOException {
        String sql = "DELETE FROM CalendarEvents WHERE username = ? AND name = ? AND startDate = ?";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, event.getName());
            pstmt.setString(3, event.getStartDate().toString());
            pstmt.executeUpdate();

            // Update the user's calendar in the UserDAO
            User user = userDAO.findByUsername(username);
            user.removeEvent(event);
            userDAO.WriteToCache(user);

        } catch (SQLException e) {
            throw new IOException("Failed to remove event", e);
        }
    }
}
