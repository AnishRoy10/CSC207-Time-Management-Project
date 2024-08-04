package data_access;

import entity.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;

import java.io.File;
import java.io.IOException;

public class CourseDataAccessObjectTest {
    private String path = "test_courses.json";
    private CourseRepository courseDataAccessObject;

    @BeforeEach
    public void setUp() throws IOException {
        this.courseDataAccessObject = new CourseDataAccessObject(path);
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (!new File(path).delete()) {
            throw new IOException("Something went wrong deleting a course test file.");
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
