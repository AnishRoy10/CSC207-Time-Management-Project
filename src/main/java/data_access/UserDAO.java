package data_access;

import entity.User;
import entity.Course;
import repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

/**
 * The UserDAO class handles CRUD operations for User objects in an SQLite database.
 * It implements the UserRepository interface.
 */
public class UserDAO implements UserRepository {

    private final SQLDatabaseHelper dbHelper;

    /**
     * Constructor for UserDAO.
     *
     * @param dbHelper An instance of SQLDatabaseHelper for managing database connections.
     */
    public UserDAO(SQLDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Writes a User object to the database. If the user already exists, updates the user's data.
     *
     * @param user The User object to write to the database.
     */
    @Override
    public void WriteToCache(User user) throws IOException {
        String sql = "INSERT INTO Users(username, password, score, courses) VALUES(?, ?, ?, ?) " +
                "ON CONFLICT(username) DO UPDATE SET password=excluded.password, score=excluded.score, courses=excluded.courses";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getScore());
            pstmt.setString(4, String.join(",", user.getCourses()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Failed to write user to cache", e);
        }
    }

    /**
     * Reads the first User object from the database.
     *
     * @return The first User object from the database, or null if the database is empty.
     */
    @Override
    public User ReadFromCache() throws IOException {
        String sql = "SELECT * FROM Users LIMIT 1";
        User user = null;

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), new User[]{}, new Course[]{});
                user.setScore(rs.getInt("score"));
                String coursesStr = rs.getString("courses");
                if (coursesStr != null && !coursesStr.isEmpty()) {
                    String[] courses = coursesStr.split(",");
                    for (String course : courses) {
                        user.addCourse(new Course(course, ""));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IOException("Failed to read user from cache", e);
        }
        return user;
    }

    /**
     * Reads a User object from the database by username.
     *
     * @param username The username of the user to read.
     * @return The User object with the specified username, or null if not found.
     */
    @Override
    public User ReadFromCache(String username) throws IOException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        User user = null;

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), new User[]{}, new Course[]{});
                user.setScore(rs.getInt("score"));
                String coursesStr = rs.getString("courses");
                if (coursesStr != null && !coursesStr.isEmpty()) {
                    String[] courses = coursesStr.split(",");
                    for (String course : courses) {
                        user.addCourse(new Course(course, ""));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IOException("Failed to read user from cache", e);
        }
        return user;
    }

    /**
     * Checks if a user exists in the database by username.
     *
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     */
    @Override
    public boolean UserExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        boolean exists = false;

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            exists = rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exists;
    }

    /**
     * Finds a user by username in the database.
     *
     * @param username The username to find.
     * @return The User object with the specified username, or null if not found.
     */
    @Override
    public User findByUsername(String username) throws IOException {
        return ReadFromCache(username);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all User objects in the database.
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"), new User[]{}, new Course[]{});
                user.setScore(rs.getInt("score"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    /**
     * Deletes a user by username from the database.
     *
     * @param username The username of the user to delete.
     */
    public void deleteUser(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
