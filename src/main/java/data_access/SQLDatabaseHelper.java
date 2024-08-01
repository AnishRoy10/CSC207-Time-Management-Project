package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The SQLDatabaseHelper class provides methods to initialize and manage the SQLite database.
 * It is used by the UserDAO class to connect to the database and perform CRUD operations.
 * The database schema includes tables for Users and Tasks.
 * The Users table stores user information such as username, password, score, and other data.
 * The Tasks table stores task information such as title, description, deadline, and other data.
 */
public class SQLDatabaseHelper {
    private static final String URL = "jdbc:sqlite:saves/UserDB.db";

    /**
     * Initializes the database by creating the necessary tables if they do not exist.
     */
    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                String createUserTable = "CREATE TABLE IF NOT EXISTS Users ("
                        + "username TEXT PRIMARY KEY,"
                        + "password TEXT NOT NULL,"
                        + "friends TEXT,"
                        + "courses TEXT,"
                        + "tasks TEXT,"
                        + "score INTEGER,"
                        + "anumber INTEGER,"
                        + "calendar TEXT"
                        + ");";

                String createTasksTable = "CREATE TABLE IF NOT EXISTS Tasks ("
                        + "id TEXT PRIMARY KEY,"
                        + "username TEXT NOT NULL,"
                        + "title TEXT NOT NULL,"
                        + "description TEXT,"
                        + "completed BOOLEAN,"
                        + "startDate TEXT,"
                        + "deadline TEXT,"
                        + "course TEXT,"
                        + "pointsAwarded BOOLEAN,"
                        + "completionDate TEXT,"
                        + "FOREIGN KEY(username) REFERENCES Users(username)"
                        + ");";

                stmt.execute(createUserTable);
                stmt.execute(createTasksTable);

                System.out.println("Database schema has been initialized.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return a Connection object to the SQLite database
     * @throws SQLException if a database access error occurs
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
