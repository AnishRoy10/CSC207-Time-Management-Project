package data_access;

import entity.Task;
import repositories.TaskRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The TaskDAO class handles CRUD operations for Task objects in an SQLite database.
 * It implements the TaskRepository interface.
 */
public class TaskDAO implements TaskRepository {

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
     * @param task The Task object to write to the database.
     * @param username The username associated with the task.
     */
    @Override
    public void WriteToCache(Task task, String username) {
        String sql = "INSERT INTO Tasks(id, title, description, completed, startDate, deadline, course, completionDate, pointsAwarded, username) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT(id) DO UPDATE SET title=excluded.title, description=excluded.description, completed=excluded.completed, " +
                "startDate=excluded.startDate, deadline=excluded.deadline, course=excluded.course, completionDate=excluded.completionDate, pointsAwarded=excluded.pointsAwarded";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getId().toString());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setBoolean(4, task.isCompleted());
            pstmt.setString(5, task.getStartDate().toString());
            pstmt.setString(6, task.getDeadline().toString());
            pstmt.setString(7, task.getCourse());
            pstmt.setString(8, task.getCompletionDate() != null ? task.getCompletionDate().toString() : null);
            pstmt.setBoolean(9, task.isPointsAwarded());
            pstmt.setString(10, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads a Task object from the database by task ID.
     *
     * @param id The task ID to read.
     * @return The Task object with the specified ID, or null if not found.
     */
    @Override
    public Task ReadFromCache(UUID id) {
        String sql = "SELECT * FROM Tasks WHERE id = ?";
        Task task = null;

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
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
                task.setCompletionDate(rs.getString("completionDate") != null ? LocalDateTime.parse(rs.getString("completionDate")) : null);
                task.setPointsAwarded(rs.getBoolean("pointsAwarded"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return task;
    }

    /**
     * Retrieves all tasks associated with a specific username from the database.
     *
     * @param username The username associated with the tasks.
     * @return A list of Task objects.
     */
    @Override
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
                task.setCompletionDate(rs.getString("completionDate") != null ? LocalDateTime.parse(rs.getString("completionDate")) : null);
                task.setPointsAwarded(rs.getBoolean("pointsAwarded"));
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
     * @param id The task ID to delete.
     */
    @Override
    public void deleteTask(UUID id) {
        String sql = "DELETE FROM Tasks WHERE id = ?";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
