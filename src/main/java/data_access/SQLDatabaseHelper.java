package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseHelper class manages SQLite database connection and schema creation.
 * This class provides methods to connect to the database and create necessary tables.
 */
public class SQLDatabaseHelper {

    // URL to connect to SQLite database located in saves/UserDB.db
    private static final String URL = "jdbc:sqlite:Saves/UserDB.db";

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return Connection object to interact with the database.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Creates a new database if it does not exist, and initializes the schema.
     * This method creates necessary tables for Users and Tasks.
     */
    public static void createNewDatabase() {
        // Establish connection and create tables
        try (Connection conn = connect()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                // Create Users table
                String sqlUsers = "CREATE TABLE IF NOT EXISTS Users ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "username TEXT NOT NULL UNIQUE, "
                        + "password TEXT NOT NULL, "
                        + "friends TEXT, "
                        + "courses TEXT, "
                        + "todo TEXT, "
                        + "score INTEGER, "
                        + "aNumber INTEGER, "
                        + "calendar TEXT"
                        + ");";
                stmt.execute(sqlUsers);

                // Create Tasks table
                String sqlTasks = "CREATE TABLE IF NOT EXISTS Tasks ("
                        + "id TEXT PRIMARY KEY, "
                        + "user_id INTEGER, "
                        + "title TEXT, "
                        + "description TEXT, "
                        + "completed BOOLEAN, "
                        + "startDate TEXT, "
                        + "deadline TEXT, "
                        + "course TEXT, "
                        + "pointsAwarded BOOLEAN, "
                        + "FOREIGN KEY (user_id) REFERENCES Users (id)"
                        + ");";
                stmt.execute(sqlTasks);

                System.out.println("Database schema has been initialized.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
