package data_access;

import entity.Calendar;
import entity.CalendarEvent;
import use_case.AddEventUseCase.AddEventDataAccessInterface;
import use_case.ViewEventsUseCase.ViewEventsDataAccessInterface;
import use_case.RemoveEventUseCase.RemoveEventDataAccessInterface;

import java.sql.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The SQL-based DAO class for managing calendar events.
 */
public class CalendarDataAccessObject
        implements ViewEventsDataAccessInterface, AddEventDataAccessInterface, RemoveEventDataAccessInterface {
    private SQLDatabaseHelper dbHelper;
    private String username;

    public CalendarDataAccessObject(String username, SQLDatabaseHelper dbHelper) {
        this.username = username;
        this.dbHelper = dbHelper;
    }

    @Override
    public Calendar getCalendar() throws IOException, ClassNotFoundException {
        Calendar calendar = new Calendar();
        List<CalendarEvent> events = getAllEvents();
        for (CalendarEvent event : events) {
            calendar.addEvent(event);
        }
        return calendar;
    }

    public List<CalendarEvent> getAllEvents() throws IOException {
        List<CalendarEvent> events = new ArrayList<>();
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
                events.add(event);
            }
        } catch (SQLException e) {
            throw new IOException("Failed to retrieve events", e);
        }
        return events;
    }

    @Override
    public void addEvent(CalendarEvent event) throws IOException {
        String sql = "INSERT INTO CalendarEvents(id, username, name, description, status, priorityLevel, startDate, endDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, UUID.randomUUID().toString()); // Generate UUID
            pstmt.setString(2, username);
            pstmt.setString(3, event.getName());
            pstmt.setString(4, event.getDescription());
            pstmt.setString(5, event.getStatus());
            pstmt.setString(6, event.getPriorityLevel());
            pstmt.setString(7, event.getStartDate().toString());
            pstmt.setString(8, event.getEndDate() != null ? event.getEndDate().toString() : null);
            pstmt.executeUpdate();
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
        } catch (SQLException e) {
            throw new IOException("Failed to remove event", e);
        }
    }
}
