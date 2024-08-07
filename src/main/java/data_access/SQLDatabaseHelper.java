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
 * The Leaderboard table stores leaderboard information for users.
 * The Course table stores course information such as course name, description, and other data.
 * The Friends table stores information about friends of users.
 * Running the default method initializes a database with url "jdbc:sqlite:saves/UserDB.db". This may be overridden for testing.
 */
public class SQLDatabaseHelper {
    private static final String DEFAULT_URL = "jdbc:sqlite:saves/UserDB.db";
    private String url;

    /**
     * Default constructor using the default database URL.
     */
    public SQLDatabaseHelper() {
        this.url = DEFAULT_URL;
    }

    /**
     * Constructor to specify a custom database URL.
     *
     * @param url The database URL to use.
     */
    public SQLDatabaseHelper(String url) {
        this.url = url;
    }

    /**
     * Initializes the database by creating the necessary tables if they do not exist.
     */
    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(this.url);
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                String createUserTable = "CREATE TABLE IF NOT EXISTS Users ("
                        + "username TEXT PRIMARY KEY,"
                        + "password TEXT NOT NULL,"
                        + "friends TEXT,"
                        + "courses TEXT,"
                        + "tasks TEXT,"
                        + "score INTEGER,"
                        + "timerStart INTEGER,"
                        + "timerEnd INTEGER,"
                        + "timerElapsed INTEGER,"
                        + "timerPause INTEGER,"
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
                        + "courseName TEXT,"
                        + "FOREIGN KEY(username) REFERENCES Users(username),"
                        + "FOREIGN KEY(courseName) REFERENCES Courses(name)"
                        + ");";

                String createLeaderboardTable = "CREATE TABLE IF NOT EXISTS Leaderboard ("
                        + "username TEXT,"
                        + "score INTEGER,"
                        + "type TEXT,"
                        + "PRIMARY KEY (username, type)"
                        + ");";

                String createCoursesTable = "CREATE TABLE IF NOT EXISTS Courses ("
                        + "name TEXT PRIMARY KEY,"
                        + "description TEXT,"
                        + "usernames TEXT,"
                        + "todoList TEXT,"
                        + "dailyLeaderboard TEXT,"
                        + "monthlyLeaderboard TEXT,"
                        + "allTimeLeaderboard TEXT"
                        + ");";

                String createFriendsTable = "CREATE TABLE IF NOT EXISTS Friends ("
                        + "username TEXT NOT NULL,"
                        + "friend_username TEXT NOT NULL,"
                        + "PRIMARY KEY (username, friend_username),"
                        + "FOREIGN KEY (username) REFERENCES Users(username),"
                        + "FOREIGN KEY (friend_username) REFERENCES Users(username)"
                        + ");";

                String createCalendarEventsTable = "CREATE TABLE IF NOT EXISTS CalendarEvents ("
                        + "username TEXT NOT NULL,"
                        + "name TEXT NOT NULL,"
                        + "description TEXT,"
                        + "status TEXT,"
                        + "priorityLevel TEXT,"
                        + "startDate TEXT NOT NULL,"
                        + "endDate TEXT,"
                        + "PRIMARY KEY(username, name, startDate),"
                        + "FOREIGN KEY(username) REFERENCES Users(username)"
                        + ");";

                stmt.execute(createUserTable);
                stmt.execute(createTasksTable);
                stmt.execute(createLeaderboardTable);
                stmt.execute(createCoursesTable);
                stmt.execute(createFriendsTable);
                stmt.execute(createCalendarEventsTable);

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
        return DriverManager.getConnection(url);
    }
}
