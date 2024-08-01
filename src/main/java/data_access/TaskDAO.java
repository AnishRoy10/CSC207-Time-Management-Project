package data_access;

import entity.Task;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The TaskDAO class handles CRUD operations for Task objects in an SQLite database.
 */
public class TaskDAO {
    private final SQLDatabaseHelper dbHelper;

    /**
     * Constructor for TaskDAO.
     *
     * @param dbHelper An instance of SQLDatabaseHelper for managing database connections.
     */
    public TaskDAO(SQLDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Writes a Task object to the database. If the task already exists, updates the task's data.
     *
     * @param task     The Task object to write to the database.
     * @param username The username of the user to whom the task belongs.
     */
    public void writeTask(Task task, String username) {
        String sql = "INSERT INTO Tasks(id, username, title, description, completed, startDate, deadline, course, pointsAwarded, completionDate) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT(id) DO UPDATE SET " +
                "username=excluded.username, title=excluded.title, description=excluded.description, " +
                "completed=excluded.completed, startDate=excluded.startDate, deadline=excluded.deadline, " +
                "course=excluded.course, pointsAwarded=excluded.pointsAwarded, completionDate=excluded.completionDate";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getId().toString());
            pstmt.setString(2, username);
            pstmt.setString(3, task.getTitle());
            pstmt.setString(4, task.getDescription());
            pstmt.setBoolean(5, task.isCompleted());
            pstmt.setString(6, task.getStartDate().toString());
            pstmt.setString(7, task.getDeadline().toString());
            pstmt.setString(8, task.getCourse());
            pstmt.setBoolean(9, task.isPointsAwarded());
            pstmt.setString(10, task.getCompletionDate() != null ? task.getCompletionDate().toString() : null);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads a Task object from the database by task ID.
     *
     * @param taskId The ID of the task to read.
     * @return The Task object with the specified ID, or null if not found.
     */
    public Task readTask(UUID taskId) {
        String sql = "SELECT * FROM Tasks WHERE id = ?";
        Task task = null;

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskId.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                task = new Task(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("title"),
                        rs.getString("description"),
                        LocalDateTime.parse(rs.getString("startDate")),
                        LocalDateTime.parse(rs.getString("deadline")),
                        rs.getString("course")
                );
                task.setCompleted(rs.getBoolean("completed"));
                task.setPointsAwarded(rs.getBoolean("pointsAwarded"));
                if (rs.getString("completionDate") != null) {
                    task.setCompletionDate(LocalDateTime.parse(rs.getString("completionDate")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return task;
    }

    /**
     * Retrieves all tasks for a specific user from the database.
     *
     * @param username The username of the user whose tasks are to be retrieved.
     * @return A list of Task objects associated with the specified user.
     */
    public List<Task> getAllTasks(String username) {
        String sql = "SELECT * FROM Tasks WHERE username = ?";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("title"),
                        rs.getString("description"),
                        LocalDateTime.parse(rs.getString("startDate")),
                        LocalDateTime.parse(rs.getString("deadline")),
                        rs.getString("course")
                );
                task.setCompleted(rs.getBoolean("completed"));
                task.setPointsAwarded(rs.getBoolean("pointsAwarded"));
                if (rs.getString("completionDate") != null) {
                    task.setCompletionDate(LocalDateTime.parse(rs.getString("completionDate")));
                }
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    /**
     * Deletes a task by task ID from the database.
     *
     * @param taskId The ID of the task to delete.
     */
    public void deleteTask(UUID taskId) {
        String sql = "DELETE FROM Tasks WHERE id = ?";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskId.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
