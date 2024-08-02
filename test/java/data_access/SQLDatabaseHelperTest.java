package data_access;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class SQLDatabaseHelperTest {
    private SQLDatabaseHelper dbHelper;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Tasks");
            stmt.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testConnection() {
        try (Connection conn = dbHelper.connect()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            fail("Connection to the database should be established without exception.");
        }
    }

    @Test
    void testDatabaseSchema() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            // Check if Users table exists
            var rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Users'");
            assertTrue(rs.next());

            // Check if Tasks table exists
            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Tasks'");
            assertTrue(rs.next());
        } catch (SQLException e) {
            fail("Database schema should be initialized without exception.");
        }
    }
}
