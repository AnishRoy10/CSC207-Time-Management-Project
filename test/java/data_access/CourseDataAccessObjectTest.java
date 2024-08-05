package data_access;

import entity.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseDataAccessObjectTest {
    private CourseRepository courseDataAccessObject;
    private SQLDatabaseHelper dbHelper;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    public void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        courseDataAccessObject = new CourseDataAccessObject(dbHelper);
    }

    @AfterEach
    public void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Courses");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testWritingCourses() {
        Assertions.assertDoesNotThrow(() -> {
            Course c1 = new Course("Test 1", "Test Desc 1");
            Course c2 = new Course("Test 2", "Test Desc 2");

            Assertions.assertTrue(courseDataAccessObject.WriteToCache(c1));
            Assertions.assertTrue(courseDataAccessObject.WriteToCache(c2));

            Assertions.assertTrue(courseDataAccessObject.courseExists("Test 1"));
            Assertions.assertTrue(courseDataAccessObject.courseExists("Test 2"));
        });
    }
}
